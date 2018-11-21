/**
 * 
 */
package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Administrator
 *
 */
public class ServerTest {

	public static final Logger logger = LoggerFactory.getLogger(ServerTest.class);

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
	 * A byte sequence that that follows a delimiter that will be followed by an
	 * encapsulation (<code>CRLF</code>).
	 */
	protected static final byte[] FIELD_SEPARATOR = { CR, LF };
	
	/**
	 * A byte sequence that marks the end of <code>header-part</code>
	 * (<code>CRLFCRLF</code>).
	 */
	protected static final byte[] HEADER_SEPARATOR = { CR, LF, CR, LF };
	
	@Test
	public void testContextType() {
		String contentType = null;
		String data = "Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryELGLyfigtKkARcNe";
		if (data.startsWith("Content-Type")) {
			contentType = data.substring(data.indexOf(":") + 1, data.indexOf(";"));
		}
		System.out.println(contentType.trim());

		data = "Content-Type: application/x-www-form-urlencoded";

	}
	
	@Test
	public void isEndStreamReadByteArray() {
		
		try {
			Class.forName("test.ThreadService");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		int port = 2222;// 端口 // 为服务器指定端口
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			// 创建服务器端的 Socket
			serverSocket = new ServerSocket(port);

			logger.debug("服务器启动成功！");

			// 等待返回客户端的信息
			socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他

			// 获得服务器的信息
			logger.debug("客户端接入成功！");
			logger.debug("ip:{}", socket.getInetAddress());
			// 服务器开放的端口是服务器随机指定的
			logger.debug("客户端端口:{}", socket.getLocalPort());// 端口

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			BufferedInputStream bis = new BufferedInputStream(is);
			logger.debug("读取请求报文起始行");
			// 读取请求头起始行
			String header = br.readLine();
			System.out.println(header);

			logger.debug("读取请求报文首部");
			String data;
			String contentType = null;
			Integer contentLength = null;
			while (!(data = br.readLine()).equals("")) {
				System.out.println(data);
				if (data.startsWith("Content-Type") && data.contains(";")) {
					contentType = data.substring(data.indexOf(":") + 1, data.indexOf(";"));
				} else if (data.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(data.split(":")[1].trim());
				}
			}
			System.out.println("");

			// 判断请求类型
			if (header.startsWith("POST")) {
//				DataInputStream dis = new DataInputStream(is);
//				String fileName = dis.readUTF();
				OutputStream out = new FileOutputStream(new File(System.currentTimeMillis()+".data"));
				byte[] buf = new byte[4096];
				int total = 0;  
				StringBuffer strbss = new StringBuffer();
				int i = 0;
				int index = 0;
				while (total < contentLength && bis.available() > 0 && (i = is.read(buf)) != -1) {
					out.write(buf, 0, i);
					total += i;
				}
				out.flush();
				out.close();
				System.out.println(total);
			}

			// 响应
			Template temp = ThreadService.cfg.getTemplate("form.ftl");

			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type: text/html; charset=utf-8");
			out.println("Server: MINISERVER");
			out.println("Keep-Alive:0");
			out.println("Connection:false");
			// this blank line signals the end of the headers
			out.println("");
			temp.process(ThreadService.root, out);
			out.println();
			out.flush();
			out.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void isEndStreamRead() {
		
		try {
			Class.forName("test.ThreadService");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		int port = 2222;// 端口 // 为服务器指定端口
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			// 创建服务器端的 Socket
			serverSocket = new ServerSocket(port);

			logger.debug("服务器启动成功！");

			// 等待返回客户端的信息
			socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他

			// 获得服务器的信息
			logger.debug("客户端接入成功！");
			logger.debug("ip:{}", socket.getInetAddress());
			// 服务器开放的端口是服务器随机指定的
			logger.debug("客户端端口:{}", socket.getLocalPort());// 端口

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			logger.debug("读取请求报文起始行");
			// 读取请求头起始行
			String header = br.readLine();
			System.out.println(header);

			logger.debug("读取请求报文首部");
			String data;
			String contentType = null;
			Integer contentLength = null;
			while (!(data = br.readLine()).equals("")) {
				System.out.println(data);
				if (data.startsWith("Content-Type") && data.contains(";")) {
					contentType = data.substring(data.indexOf(":") + 1, data.indexOf(";"));
				} else if (data.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(data.split(":")[1].trim());
				}
			}
			System.out.println("");

			// 判断请求类型
			if (header.startsWith("POST")) {
				OutputStream out = new FileOutputStream(new File(System.currentTimeMillis()+".data"));
				byte[] buf = new byte[4096];
				int total = 0;  
				StringBuffer strbss = new StringBuffer();
				int i = 0;
				int index = 0;
				while (total < contentLength &&(i = br.read()) != -1) {
//				while (br.ready() && (i = br.read()) != -1) {
					if(i > 255) {
						System.out.println(i);
					}
					strbss.append(i);
					strbss.append(" ");
					buf[index] = (byte) i;
					index++;
					if(index - buf.length == 0) {
						out.write(buf);
						index = 0;
					}
					total++;
				}
				out.write(buf, 0, index);
				out.flush();
				out.close();
				System.out.println(total);
			}

			// 响应
			Template temp = ThreadService.cfg.getTemplate("form.ftl");

			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type: text/html; charset=utf-8");
			out.println("Server: MINISERVER");
			out.println("Keep-Alive:0");
			out.println("Connection:false");
			// this blank line signals the end of the headers
			out.println("");
			temp.process(ThreadService.root, out);
			out.println();
			out.flush();
			out.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void readStreamAllOnly() {
		
		try {
			Class.forName("test.ThreadService");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		int port = 2222;// 端口 // 为服务器指定端口
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			// 创建服务器端的 Socket
			serverSocket = new ServerSocket(port);

			logger.debug("服务器启动成功！");

			// 等待返回客户端的信息
			socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他

			// 获得服务器的信息
			logger.debug("客户端接入成功！");
			logger.debug("ip:{}", socket.getInetAddress());
			// 服务器开放的端口是服务器随机指定的
			logger.debug("客户端端口:{}", socket.getLocalPort());// 端口

			InputStream is = socket.getInputStream();

			Reader reader = new InputStreamReader(is, "iso-8859-1");
			
			BufferedReader br = new BufferedReader(reader);
			
			OutputStream output = new FileOutputStream(new File(System.currentTimeMillis()+".tmp"));
			
			int i = 0;
			int index = 0;
			byte[] buf = new byte[2048];
			while (br.ready() && (i = br.read()) != -1) {

				buf[index] = (byte) i;

				index++;

				if (index - buf.length == 0) {
					output.write(buf, 0, buf.length - 1);
					index = 0;
				}
			}
			
			if (index != 0) {
				output.write(buf, 0, index - 1);
			}
			
			output.flush();
			output.close();
			
			// 响应
			Template temp = ThreadService.cfg.getTemplate("form.ftl");

			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type: text/html; charset=utf-8");
			out.println("Server: MINISERVER");
			out.println("Keep-Alive:0");
			out.println("Connection:false");
			// this blank line signals the end of the headers
			out.println("");
			temp.process(ThreadService.root, out);
			out.println();
			out.flush();
			out.close();
			is.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readStreamOnly() {
		
		try {
			Class.forName("test.ThreadService");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		int port = 2222;// 端口 // 为服务器指定端口
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			// 创建服务器端的 Socket
			serverSocket = new ServerSocket(port);

			logger.debug("服务器启动成功！");

			// 等待返回客户端的信息
			socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他

			// 获得服务器的信息
			logger.debug("客户端接入成功！");
			logger.debug("ip:{}", socket.getInetAddress());
			// 服务器开放的端口是服务器随机指定的
			logger.debug("客户端端口:{}", socket.getLocalPort());// 端口

			InputStream is = socket.getInputStream();
			
			// 先读满一个 byte 数组缓冲区
			// TODO headBuffer 数组缓冲区,值设置可能较大如果请求内容很少则会存在阻塞
			int data = 0;
			byte bData = 0;
			byte[] headBuffer = new byte[4096];
			int headReadLength = 0;
			for (; headReadLength < headBuffer.length; headReadLength++) {
				if ((data = is.read()) != -1) {
					if (data > 255) {
						logger.error("读取到一个大于 255 的字节: {} ", data);
					}
					bData = (byte) data;

					headBuffer[headReadLength] = bData;
				} else {
					break;
				}
			}

			OutputStream os = new FileOutputStream(new File(System.currentTimeMillis() + ".tmp"));
			
			// 请求部首的集合
			ArrayList<String> headPartList = new ArrayList<>();
			
			byte[] headPart = new byte[1024];
			int headPartIndex = 0;
			int headIndex = 0;
			int startIndex = 0;
			int y = 0;
			
			for(;;) {
				if(headIndex < headReadLength) {
					// 处理读取到的 buffer
					// 读到 \r\n\r\n 后退出，Head 读完了
					if (headBuffer[headIndex] == CR 
							&& headBuffer[headIndex + 1] == LF
							&& headBuffer[headIndex + 2] == CR
							&& headBuffer[headIndex + 3] == LF) {
						
						if(headPartIndex > 0) {
							if (headPartIndex + 3 <= headPart.length) {
								byte[] temp = new byte[headPartIndex + 3];
								System.arraycopy(headPart, 0, temp, 0, temp.length - 4);
								System.arraycopy(HEADER_SEPARATOR, 0, temp, temp.length - 4, HEADER_SEPARATOR.length);

								// 将读取内容写入到文件
								os.write(temp);
								// 重置数组下标
								headPartIndex = 0;
								startIndex = 0;
							}else {
								byte[] temp = new byte[headPartIndex];
								System.arraycopy(headPart, 0, temp, 0, temp.length);
								// 将读取内容写入到文件
								os.write(temp);
								// 写入换行符
								os.write(HEADER_SEPARATOR);
								// 重置数组下标
								headPartIndex = 0;
								startIndex = 0;
							}
						}
						
						headIndex += 3; // 跳过后续的 \n\r\n
						
						break;
					} else if (headBuffer[headIndex] == CR 
							&& headBuffer[headIndex + 1] == LF) {
						
						// 问题
						if(startIndex == 0) {
							// 不做任何处理，本次循环结束后将 headPartIndex 赋值给 startIndex
							y = headPartIndex;
						}else {
							startIndex += 2; // 过滤换行字符
							y = headPartIndex - startIndex;
						}
						
						byte[] temp = new byte[y];
						System.arraycopy(headPart, startIndex, temp, 0, temp.length);
						String tempStr = new String(temp);
						headPartList.add(tempStr);
						
						y = 0; // 将间距重新置为 0 
						startIndex = headPartIndex;
					}
					
					// 对读取数据进行重新赋值
					headPart[headPartIndex] = headBuffer[headIndex];
					// 数组下标自增
					headPartIndex ++;
					
					if(headPartIndex == headPart.length) {
						
						// 创建临时字节数据
						byte[] temp = new byte[headPartIndex - 1];
						// 拷贝读取数据到 temp 字节数组
						System.arraycopy(headPart, 0, temp, 0, temp.length);
						// 将读取内容写入到文件
						os.write(temp);
						// 重置数组下标
						headPartIndex = 0;
						startIndex = 0;
					}
					
				}else {
					break;
				}
				
				headIndex ++;
			}
			
			int readLength =  0;
			
			// 处理读取请求头时剩下的数据
			if(headIndex < headBuffer.length) {
				// 获取还剩余的 HTTP 请求体的部分长度
				readLength = headBuffer.length - headIndex;
				// 创建临时字节数据
				byte[] temp = new byte[headBuffer.length - headIndex];
				// 拷贝读取数据到 temp 字节数组
				System.arraycopy(headBuffer, headIndex, temp, 0, temp.length);
				// 释放 headBuffer
				headBuffer = null;
				// 写入到文件中
				os.write(temp);
			}
			// 处理请求头
			// 获取 HTTP 首行，获取请求类型协议，协议版本
			// 获得 Content-Length 判断是否读到流的末尾

			Integer contentLength = null;
			
			Map<String,String> headMap = new HashMap<String,String>();
			
			// 请求头以读取完毕，已写入文件完毕
			for (String string : headPartList) {
				// 输出读取请求头内容
				System.out.println(string);
				String[] heads = string.split(": ");
				if (heads.length == 1) {
					headMap.put("protocol", heads[0]);
				} else if (heads.length == 2) {
					headMap.put(heads[0], heads[1]);
				}
				if(string.startsWith("Content-Length")) {
					if(contentLength == null) {
						contentLength = Integer.parseInt(heads[1]);
					}
				}
			}
			
			// 已读取数据数长度为 headIndex
			// headBuffer 长度为 4096
			// HTTP 请求体内容长度为 contentLength
			// 4096 - headIndex 为除了 HTTP 首部外多读取写入文件的字节
			int relLength = contentLength - readLength;
			relLength = contentLength - 4096;
			// 读取 HTTP 请求体的缓存数组
			byte[] buf = new byte[4096];
			int index = 0;
			// 继续读取 InputStream 数据流直到读到流的结束
			
			for (;;) {
				
				if(index == 29484) {
					System.out.println(index);
				}
				
				if(relLength <= index) {
					break;
				}
				
				if ((data = is.read()) != -1) {
					
					if (data > 255) {
						logger.error("读取到一个大于 255 的字节: {} ", data);
					}
					
					bData = (byte) data;

					buf[index] = bData;
					index ++;
					
					if(buf.length == index) {
						os.write(buf,0,index - 1);
						index = 0;
					}
					
				} else {
					break;
				}
			}
			
			if (index != 0) {
				os.write(buf,0,index);
			}
			
			os.flush();
			os.close();
			
			// 响应
			Template temp = ThreadService.cfg.getTemplate("form.ftl");

			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type: text/html; charset=utf-8");
			out.println("Server: MINISERVER");
			out.println("Keep-Alive:0");
			out.println("Connection:false");
			// this blank line signals the end of the headers
			out.println("");
			temp.process(ThreadService.root, out);
			out.println();
			out.flush();
			out.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 输出 HTTP 首部
	 * 输出 HTTP GET POST 内容
	 * 保存 文件保存 POST FieldItem 
	 * 保存 FileItem 读取出的 byte 字节
	 */
	@Test
	public void run() {

		try {
			Class.forName("test.ThreadService");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		int port = 2222;// 端口 // 为服务器指定端口
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			// 创建服务器端的 Socket
			serverSocket = new ServerSocket(port);

			logger.debug("服务器启动成功！");

			// 等待返回客户端的信息
			socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他

			// 获得服务器的信息
			logger.debug("客户端接入成功！");
			logger.debug("ip:{}", socket.getInetAddress());
			// 服务器开放的端口是服务器随机指定的
			logger.debug("客户端端口:{}", socket.getLocalPort());// 端口

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			logger.debug("读取请求报文起始行");
			// 读取请求头起始行
			String header = br.readLine();
			System.out.println(header);

			logger.debug("读取请求报文首部");
			String data;
			String contentType = null;
			Integer contentLength = null;
			while (!(data = br.readLine()).equals("")) {
				System.out.println(data);
				if (data.startsWith("Content-Type") && data.contains(";")) {
					contentType = data.substring(data.indexOf(":") + 1, data.indexOf(";"));
				} else if (data.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(data.split(":")[1].trim());
				}
			}
			System.out.println("");

			// 判断请求类型
			if (header.startsWith("POST")) {
				OutputStream out = new FileOutputStream(new File(System.currentTimeMillis()+".data"));
				byte[] buf = new byte[4096];
				byte[] buf2 = new byte[1024];
				int total = 0;  
//				for (;;) {
//	                int res = is.read(buf);
//	                if (res == -1) {
//	                    break;
//	                }
//	                if (res > 0) {
//	                    total += res;
//	                    if (out != null) {
//	                        out.write(buf, 0, res);
//	                    }
//	                }
//	                if(total == 24764) {
//	                	break;
//	                }
//	                System.out.println(total);
//	            }
//				System.out.println(total);
				File file = new File(System.currentTimeMillis()+".txt");
				OutputStream os = new FileOutputStream(file);
				StringBuffer strbss = new StringBuffer();
				StringBuffer strbContent = new StringBuffer();
				int i = 0;
				int index = 0;
				while (br.ready() && (i = br.read()) != -1) {
					strbss.append(i);
					strbss.append(" ");
					buf[index] = (byte) i;
					strbContent.append((char) i);
//					buf2[index] = (byte) i;
					index++;
					if(index - buf.length == 0) {
						out.write(buf);
						index = 0;
					}
					total++;
				}
				out.write(buf, 0, index);
				os.write(strbss.toString().getBytes());
				System.out.println(strbContent);
//				out.flush();
//				out.close();
				System.out.println(total);
//				for (int j = 0; j < index; j++) {
//					System.out.println(buf2[j]);
//				}
//				out.write(buf2, 0, index);
				//ss(br, data, contentType, contentLength);
			}

			// 响应
			Template temp = ThreadService.cfg.getTemplate("form.ftl");

			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type: text/html; charset=utf-8");
			out.println("Server: MINISERVER");
			out.println("Keep-Alive:0");
			out.println("Connection:false");
			// this blank line signals the end of the headers
			out.println("");
			temp.process(ThreadService.root, out);
			out.println();
			out.flush();
			out.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	private void ss(BufferedReader br, String data, String contentType, Integer contentLength)
			throws FileNotFoundException, IOException {
		// 判断请求内容类型 Content-Type: multipart/form-data;
		if (contentType != null && contentType.trim().equals("multipart/form-data")) {
			logger.debug("读取请求报文主体");
			if (null != contentLength && contentLength > 0) {
				File file = new File("" + System.currentTimeMillis() + ".data");
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] buffer = new byte[1024];
				int readLength = 0;
				int totalRead = 0;
//						while((readLength = is.read(buffer, 0, buffer.length)) > -1) {
//							bos.write(buffer, 0, readLength);
//							totalRead += readLength;
//						}
				int i = 0;
				int index = 0;
				while (br.ready() && (i = br.read()) != -1) {
					buffer[index] = (byte) i;
					index++;
					if(index - buffer.length == 0) {
						bos.write(buffer);
						index = 0;
					}
				}
				bos.write(buffer, 0, index -1);
				bos.flush();
				bos.close();
				System.out.println(totalRead);
			}
			
//					while (!(data = br.readLine()).endsWith("--")) {
//						System.out.println(data);
//					}
//					StringBuffer stringBuffer = new StringBuffer();
//					int i = 0;
//					while (br.ready() && (i = br.read()) != -1) {
//						stringBuffer.append((char) i);
//					}
			logger.debug(data);
		} else {
			StringBuffer stringBuffer = new StringBuffer();
			int i = 0;
			while (br.ready() && (i = br.read()) != -1) {
				stringBuffer.append((char) i);
			}
			System.out.println(stringBuffer.toString());
		}
	}

}
