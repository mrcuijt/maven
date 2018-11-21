package test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class FileItemReadTest {

	/**
	 * The Carriage Return ASCII character value.
	 */
	public static final byte CR = 0x0D;

	/**
	 * The Line Feed ASCII character value.
	 */
	public static final byte LF = 0x0A;

	/**
	 * The dash (-) ASCII character value.
	 */
	public static final byte DASH = 0x2D;

	/**
	 * The maximum length of <code>header-part</code> that will be processed (10
	 * kilobytes = 10240 bytes.).
	 */
	public static final int HEADER_PART_SIZE_MAX = 10240;

	/**
	 * The default length of the buffer used for processing a request.
	 */
	protected static final int DEFAULT_BUFSIZE = 4096;

	/**
	 * A byte sequence that marks the end of <code>header-part</code>
	 * (<code>CRLFCRLF</code>).
	 */
	protected static final byte[] HEADER_SEPARATOR = { CR, LF, CR, LF };

	/**
	 * A byte sequence that that follows a delimiter that will be followed by an
	 * encapsulation (<code>CRLF</code>).
	 */
	protected static final byte[] FIELD_SEPARATOR = { CR, LF };

	/**
	 * A byte sequence that that follows a delimiter of the last encapsulation in
	 * the stream (<code>--</code>).
	 */
	protected static final byte[] STREAM_TERMINATOR = { DASH, DASH };

	/**
	 * A byte sequence that precedes a boundary (<code>CRLF--</code>).
	 */
	protected static final byte[] BOUNDARY_PREFIX = { CR, LF, DASH, DASH };

	private static int head;

	// 最终间隔字符序列 byte 数组
	private static byte[] boundary;

	private static int boundaryLength;

	private static byte[] buffer;

	private static InputStream input;
	
	// other
	private static int tail;

	@Test
	public void parse() {

		String boundaryPix = "----WebKitFormBoundaryfPABsMMWR9KFl488";

		// int boundaryLength = 0;

		// 请求报文首部中的 boundary
		byte[] boundary = boundaryPix.getBytes();

		// 最终间隔字符序列 byte 数组长度
		this.boundaryLength = boundary.length + BOUNDARY_PREFIX.length;

		// 输入流缓冲数组
		buffer = new byte[DEFAULT_BUFSIZE];

		// 最终间隔字符序列 byte 数组
		this.boundary = new byte[this.boundaryLength];

		// 将 \r\n-- 填充到 最终间隔字符序列 byte 数组 中
		System.arraycopy(BOUNDARY_PREFIX, 0, this.boundary, 0, BOUNDARY_PREFIX.length);

		// 将 请求报文首部中的 boundary 填充到 最终间隔字符序列 byte 数组 中
		System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length, boundary.length);

		try {

			File file = new File("2.tmp");

			InputStream is = new FileInputStream(file);

			byte[] marker = new byte[2];
			boolean nextChunk = false;

			head += boundaryLength;

			// 将输入流缓冲字节数组中,读取一块处理一块
			int redLength = is.read(buffer, 0, buffer.length);
			
			System.arraycopy(this.boundary, 2, this.boundary, 0, this.boundary.length - 2);
			
			// 处理 \r\n
			// 处理 --
			
			// 去除前缀比较 \r\n
			if (arrayequals(buffer, this.boundary, boundaryLength - 2)) {
				
				// 去除分割字符序列，去除换行开始读取内容体类型
				//head -= 2;
				
				// buffer 缓冲区读取数据的偏移量
				int pos = 0;
				// 读取出的数据的缓冲区
				byte[] buf = new byte[DEFAULT_BUFSIZE];
				// 缓冲区以填充的数据量
				int index = 0;
				// 读取内容描述和内容类型的处理
				int headss = 0;
				for (;;) {

					// 向下读取直到遇到 \r\n
					byte b = buffer[head + pos];
					
					buf[index] = b; // 保存读取来的字节
					index++; // 
					if (headss < 2) { // 判断是否已经完成了内容描述和内容类型行的读取
						
						// 判断是否读取完了一行，遇到换行的标识
						// 判断当前读取字节是否是 \r 判断下一个字节是否是 \n
						if (b == CR && buffer[head + pos + 1] == LF) {
							// 创建已读取字节的字节数组
							byte[] btt = new byte[index - 1]; // - 1 去除读取到回车符
							// 将已读取字节数组拷贝到 btt 字节数组中
							System.arraycopy(buf, 0, btt, 0, index - 1); // - 1 去除读取到回车符
							// 转换为 String 字符串输出
							System.out.println(new String(btt));
							
							pos += 2; // 跳过 \n
							
							// 重新构建一个跟 buffer 缓冲区一样长度的读取数据缓冲数组
							buf = new byte[DEFAULT_BUFSIZE];
							index = 0; // 将读取数据内容缓冲区 buff 的下重置为 0 
							headss++; // 已读取内容行数 + 1
							continue;
						}
					}else { // 如何已读取两行数据则退出
						if (b == CR && buffer[head + pos + 1] == LF) {// 判断是否是换行
							pos += 2; 
						}
						break; 
					}
					pos++; // 数组缓冲区偏移量
					// 设置退出条件
					if((pos + head) >= buffer.length) {
						break;
					}
				}
				
				// 需要处理一个空行
				if (buffer[head + pos] == CR && buffer[head + pos + 1] == LF) {// 判断是否是换行
					pos += 2; 
					System.out.println(buffer[head + pos]);
					System.out.println(buffer[head + pos + 1]);
				}
				
				// 缓冲区以填充的数据量
				index = 0;
				
				// 为缓冲区偏移量加上 boundary 长度
				pos += head;
				
				// 创建一个输出流
				OutputStream out = new FileOutputStream(new File(System.currentTimeMillis() + ".jpg"));
				
				// 读取处理剩下的数据
				for (;;) {

					// 向下读取直到遇到 \r\n
					byte b = buffer[pos];
					buf[index] = b;
					index++;
					if (b == CR 
							&& buffer[pos + 1] == LF 
							&& buffer[pos + 2] == DASH
							&& buffer[pos + 3] == DASH) {
						
						byte[] btt = new byte[index - 1]; // 创建一个已读取数据长度的 byte 数组
						System.arraycopy(buf, 0, btt, 0, index - 1); // 将已读取出的内容赋值给 byte 数组
						
						// 最终数据段
						out.write(btt);
						break;
					}
					
					pos++;

					// 设置退出条件，当前读取的数据缓冲区的末尾
					if (pos >= redLength) {
						System.arraycopy(buf, 0, buf, 0, index);
						out.write(buf, 0, index);
						// 重新读取数据到字节缓冲区中
						redLength = is.read(buffer, 0, buffer.length);
						// 重新设置字节缓冲区读取字节的偏移量
						pos = 0;
						buf = new byte[redLength];
						//
						index = 0;
						continue;
					}
				}
			}

			// 从缓冲字节数组中读取数据，根据分隔字符序列处理分段数据

			// 如何读取流镜像控制循环的
			// Streams.copy

			// 如何使用标识符进行分割块的

			// 如何将多余的块中的数据保存，并和下一次读取的数据拼在一起处理

			// 如何对比字节缓冲数组中的值

			// \r\n-- 匹配到下一个 \r\n

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Compares <code>count</code> first bytes in the arrays <code>a</code> and
	 * <code>b</code>.
	 *
	 * @param a     The first array to compare.
	 * @param b     The second array to compare.
	 * @param count How many bytes should be compared.
	 *
	 * @return <code>true</code> if <code>count</code> first bytes in arrays
	 *         <code>a</code> and <code>b</code> are equal.
	 */
	public static boolean arrayequals(byte[] a, byte[] b, int count) {
		for (int i = 0; i < count; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

    /**
     * Searches for a byte of specified value in the <code>buffer</code>,
     * starting at the specified <code>position</code>.
     *
     * @param value The value to find.
     * @param pos   The starting position for searching.
     *
     * @return The position of byte found, counting from beginning of the
     *         <code>buffer</code>, or <code>-1</code> if not found.
     */
    protected int findByte(byte value,
            int pos) {
        for (int i = pos; i < tail; i++) {
            if (buffer[i] == value) {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * Searches for the <code>boundary</code> in the <code>buffer</code>
     * region delimited by <code>head</code> and <code>tail</code>.
     *
     * @return The position of the boundary found, counting from the
     *         beginning of the <code>buffer</code>, or <code>-1</code> if
     *         not found.
     */
    protected int findSeparator() {
        int first;
        int match = 0;
        int maxpos = tail - boundaryLength;
        for (first = head; first <= maxpos && match != boundaryLength; first++) {
            first = findByte(boundary[0], first);
            if (first == -1 || first > maxpos) {
                return -1;
            }
            for (match = 1; match < boundaryLength; match++) {
                if (buffer[first + match] != boundary[match]) {
                    break;
                }
            }
        }
        if (match == boundaryLength) {
            return first - 1;
        }
        return -1;
    }
    
	private void demo() {
		try {

			File file = new File("1542375613282.data");

			InputStream is = new FileInputStream(file);

			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// 先读四行
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			System.out.println(br.readLine());

			// 向下读取内容
			byte[] buffer = new byte[1024];
			File dataFile = new File(System.currentTimeMillis() + ".jpg");
			FileOutputStream fos = new FileOutputStream(dataFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int readLength = 0;
			while ((readLength = is.read(buffer, 0, buffer.length)) > -1) {
				bos.write(buffer, 0, readLength);
			}
			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
