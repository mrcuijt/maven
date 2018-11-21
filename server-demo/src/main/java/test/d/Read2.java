/**
 * 
 */
package test.d;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import module.FieldItemModule;
import util.ByteUtil;

/**
 * @author Administrator
 *
 */
public class Read2 {


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
	
	
	// 存储所有解析数据的 dataMap
	Map<String,Object> dataMap = new HashMap<String,Object>();
	
	// 请求部首的 headMap
	Map<String,String> headMap = new HashMap<String,String>();
	
	// 请求部首的集合
	ArrayList<String> headPart = new ArrayList<>();
	
	// 
	ArrayList<FieldItemModule> fieldItemList = new ArrayList<>();
	
	@Test
	public void readHttpData(){
		
		File file = null;
		
		InputStream input = null;
		
		BufferedInputStream bis = null;
		
		StringBuffer strb = null;
		
		FieldItemModule fieldItemModule = null;
		
		int contentLength = 0; // 长度内容
		
		// 字节数组缓冲中，在内存中缓存输入流中读取的部分文件二进制流
		byte[] buffer = new byte[1124];
		
		int pos = 0; // 字节缓冲区中已处理数据的偏移量
		
		// InputStream 读取出的数据长度
		int readLength = 0;
		
		String contentType = null;
		
		// 判断内容类型是否是 multipart 
		boolean multipart = false;
				
		String boundary = null;  // 从 Content-Type 中读取到的 boundary
		
		byte[] bBoundary = null; // 从 Content-Type 中读取到的 boundary byte[]
		
		int iBoundaryLength = 0; // 从 Content-Type 中读取到的 boundary byte[].length
		
		byte[] bRelbBoundary = null; // 真正要使用到的 boundary = -- + boundary byte[]
		
		// HTTP 首部是否读取完毕
		boolean headRead = false;
		
		// HTTP Body 体是否读取完毕
		boolean bodyRead = false;

		// POST multipart/form-data 中的一个 field item 的 首部是否读取完成
		boolean fieldItemHeadRead = false;
		
		boolean fieldItemBodyRead = false;
		
		boolean single = false;
		
		byte[] arrayBuffer = new byte[1124]; // 暂与读取到的缓存数组大小保持一致
		int writeLength = 0;
		
		try {

			file = new File("1542707070171.tmp");

			input = new FileInputStream(file);
			
			bis = new BufferedInputStream(input);

			fieldItemModule = new FieldItemModule();
			
			// 读取一次 // TODO 后续需要改成每次只读一个字节
			while ((readLength = bis.read(buffer, 0, buffer.length)) != -1) {

				// 处理每一次得到的数据内容
				// 读取到 \r\n 表示这一行的结束
				// 读取数据第一行获取请求首行，获取到协议、协议版本、请求类型
				// 越界问题，在缓冲区的最后一个元素判断到了 \r 在向下判断时需要判断 \n 时需要再去从 InputStream 中读取后续的内容了
				// 问题
				//   1. 现在已读取到未处理完成保存的数据如何处理
				//   2. 
				
				// 做一些数据判断处理，方便主流程中的操作
				// 1、判断是否存在本行的结束
				// 2、判断是否存在下一段的开始标记
				// 3、
				
//				int i = 0;
				
				// 处理到 boundary 
				// 以 \r\n 结束 new String 添加到 headPart 中
				
				if (!headRead) {

//					for (; i < readLength; i++) {
					for(;;) {

						if (pos >= readLength)
							break;
						
						byte byteItem = buffer[pos];

						// TODO 需要先判断后续操作是否存在数组越界
						if (byteItem == CR 
								&& buffer[pos + 1] == LF 
								&& buffer[pos + 2] == DASH 
								&& buffer[pos + 3] == DASH) { // 判断
																														// CR
							if(writeLength > 0) {
								
								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								// 本行读取结束
								headPart.add(new String(headData));

								// TODO 思考是否要将 arrayBuffer 也置空

								writeLength = 0;
								
							}
							
							pos += 2;
							
							for (String head : headPart) {
								String[] arryHead = head.split(": ");
								if(arryHead.length == 1) {
									headMap.put("protocol", arryHead[0]);
								}else if(arryHead.length == 2) {
									headMap.put(arryHead[0], arryHead[1]);
								}
							}
							
							byte[] bContentType = headMap.get("Content-Type").getBytes();
							
							// 获取 ContentType & boundary
							for (int i = 0; i < bContentType.length; i++) {
								
								if(bContentType[i] == (byte)';') {
									byte[] data = new byte[i];
									System.arraycopy(bContentType, 0, data, 0, data.length);
									contentType = new String(data);
//									System.out.println(contentType);
									continue;
								}
								
								if(bContentType[i] == (byte)'=') {
									byte[] data = new byte[bContentType.length - i - 1];
									System.arraycopy(bContentType, i + 1, data, 0, data.length);
									bBoundary = data;
									boundary = new String(bBoundary);
									iBoundaryLength = bBoundary.length;
//									System.out.println(boundary);
									break;
								}
							}
							
							if(null != contentType && contentType.equals("multipart/form-data")) {
								multipart = true;
							}
							
							// \r\n-- + boundary
							bRelbBoundary = new byte[BOUNDARY_PREFIX.length + bBoundary.length];
							
							// 将 \r\n-- 填充到 最终间隔字符序列 byte 数组 中
							System.arraycopy(BOUNDARY_PREFIX, 0, bRelbBoundary, 0, BOUNDARY_PREFIX.length);

							// 将 请求报文首部中的 boundary 填充到 最终间隔字符序列 byte 数组 中
							System.arraycopy(bBoundary, 0, bRelbBoundary, BOUNDARY_PREFIX.length, bBoundary.length);
							
							// \r\n-- + boundary
							
							// 去除 \r\n
							System.arraycopy(bRelbBoundary, 2, bRelbBoundary, 0, bRelbBoundary.length - 2);
							
							// HTTP 首部读取完毕
							headRead = true;
							
							break; // buffer[pos] == \n | buffer[pos + 1] == \n | buffer[pos + 2] == - | buffer[pos + 3] == -

						} else if (byteItem == CR && buffer[pos + 1] == LF) {

							if (writeLength > 0) {

								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								// 本行读取结束
								headPart.add(new String(headData));

								// TODO 思考是否要将 arrayBuffer 也置空

								writeLength = 0;

							}

							pos += 2; // buffer[pos + 1] == \n 下次循环跳过 \n
							
							continue;
							
						}
						
						// 存入缓存数组中
						arrayBuffer[writeLength] = byteItem;

						writeLength++;
						
						pos++;
						
					} // -- headRead loop end
					
					if(headRead) {
						break;
					}
				} // headRead end

			}// -- read Stream loop end
			
			// 判断是否需要处理 buffer 中为使用完的数据
			// 首先需要知道当前 buffer 的位置
			// pos 代表 buffer 的偏移量
			// 如何操作呢？
			
			// 1.直接继续向下处理完 buffer 吗？
			//   1）开始处理，解析 field item
			//     a）处理两部分数据 field item HEAD - 将 fieldItemHeadRead 置为 true
			//     b）处理两部分数据 field item BODY - 将 fieldItemBodyRead 置为 true
			//     c）将 pos 置为 0 。
			//     问题：剩余长度不够，读取一半，需要一个临时变量存储读取出来的值
			// 2.将 buffer 缓冲区重新对其？使用一个临时变量重新定义 buffer 的长度
			// 3.将 buffer 缓冲区重新对其，并继续读取 InputStream 将其填充完整（不合适）
			
			// 只需判断处理 HTTP body 体即可
			
			
			
			
			
			
			
			// buffer pos 以外的参数都可以置空
			// 重新初始化
			writeLength = 0;
				
			if (multipart) { // 判断 Content-Type 是否是 multipart
				
				// 首次处理预防措施
				if (pos > 0) { // 判断是否需要重新对其 buffer 缓冲区

					// 如何处理 buffer 剩下的内容，一半已经作为 HTTP 首部信息读取过了
					// 做着一步操作是为了将 buffer 重新对其，比对现在开始数据是否是 boundary 。
					// 判断 pos 偏移量是否为 0
					int useLength = 0; // 使用 useLength 代替 buffer

					if (pos > 0) { // 如果有多个 field item 项就需要判断多次
						// 获取 buffer 的长度
						useLength = buffer.length; 
						// 重新对其 buffer 
						System.arraycopy(buffer, pos, buffer, 0, buffer.length - pos);
						// 获取 buffer 剩余可用长度
						useLength = buffer.length - pos;
						// 重置 buffer 偏移量
						pos = 0;
					}

					// head & body
					// 
					
					// 开始准备读取
					// 判断是否是 field item 块开始，field item 的开始需要处理 field item 头部信息
					// TODO 存在问题，现有 buffer 中 boundary 不全
					if(ByteUtil.arrayequals(buffer, bRelbBoundary, bRelbBoundary.length - 2)) {
						
						// 读取 field item 头部信息，读取完毕后将 fieldItemHeadRead 设置为 true
						
						// boundary 相同过滤掉此行 // 判断最后 2 个字节是否是 \r\n（代表读取本行的结束）
						pos += bRelbBoundary.length;
						
						for (;;pos++) { // field item head start

							if (pos >= useLength) { // pos 为下标偏移量从 0 开始，useLength 为长度从 1 开始 （pos == useLength）== buffer[pos] = 数组越界
								break; // 退出循环，head 未处理完
							}

							byte byteItem = buffer[pos];

							// 向下读取两行读到连续的两个 \r\n\r\n 后，field item 首部读取完毕

							// 判断当前读取的内容和后面连续的内容
							if (byteItem == FIELD_SEPARATOR[0] 
									&& buffer[pos + 1] == FIELD_SEPARATOR[1]
									&& buffer[pos + 2] == FIELD_SEPARATOR[0] 
									&& buffer[pos + 3] == FIELD_SEPARATOR[1]) {

								if (writeLength > 0) {

									byte[] headData = new byte[writeLength];

									System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

									// 本行读取结束
									fieldItemModule.getHeadItem().add(new String(headData));
									
									// TODO 思考是否要将 arrayBuffer 也置空

									writeLength = 0;

								}
								
								pos += 4;
								
								for (String fieldItemHead : fieldItemModule.getHeadItem()) {
									String[] heads = fieldItemHead.split(": ");
									fieldItemModule.getHeadMap().put(heads[0], heads[1]);
								}
								
								// 设置后续的内容类型
								fieldItemModule.setContentType(fieldItemModule.getHeadMap().get("Content-Type"));
								// 设置内容类型描述
								String[] contentDisposition = fieldItemModule.getHeadMap().get("Content-Disposition").split("; ");
								fieldItemModule.setContentDisposition(contentDisposition[0]);
								for (int i = 1; i < contentDisposition.length; i++) {
									
									String strContentDisposition = contentDisposition[i];
									int begin = strContentDisposition.indexOf("\"");
									int end = strContentDisposition.lastIndexOf("\"");
									
									// 文件名
									if (strContentDisposition.indexOf("filename") != -1) {
										if (begin != -1 && end != -1 && begin != end) {
											fieldItemModule.setFileName(strContentDisposition.substring(begin + 1, end));
										}
									
									// 字段名
									} else if (strContentDisposition.indexOf("name") != -1) {
										if (begin != -1 && end != -1 && begin != end) {
											fieldItemModule.setName(strContentDisposition.substring(begin + 1, end));
										}
									}
									// 字段值
								}
								
								fieldItemHeadRead = true;
								
								break; // 退出循环 Field Item 首部已经读取完毕

							} else if (byteItem == FIELD_SEPARATOR[0] 
									&& buffer[pos + 1] == FIELD_SEPARATOR[1]) {
								
								if (writeLength > 0) {

									byte[] headData = new byte[writeLength];

									System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

									// 本行读取结束
									fieldItemModule.getHeadItem().add(new String(headData));
									
									// TODO 思考是否要将 arrayBuffer 也置空

									writeLength = 0;

								}

								pos += 1; // buffer[pos + 1] == \n 下次循环跳过 \n
								
								continue;// 本行读取完毕
							}

							// 存入缓存数组中
							arrayBuffer[writeLength] = byteItem;

							writeLength++;
							
						} // field item head end

					} // if boundary end
					
					
					
					if(fieldItemHeadRead && !fieldItemBodyRead) { // if fieldItemBodyRead 
						
						// TODO 关键点对 useLength 做了二次取舍
						if (pos > 0) { // 如果有多个 field item 项就需要判断多次
							int usedLength = (buffer.length - useLength) + pos;
							// 重新对其 buffer 
							System.arraycopy(buffer, usedLength, buffer, 0, buffer.length - usedLength);
							// 获取 buffer 剩余可用长度
							useLength = (buffer.length - usedLength);
							// 重置 buffer 偏移量
							pos = 0;
						}
						
						// 开始读取二进制数值
						for (;;) {
							
//							System.out.println(writeLength);

							if(useLength > 0) {
								if (pos >= useLength) {
									fieldItemModule.getOutput().write(arrayBuffer, 0, writeLength);
									useLength = 0;
									pos = 0;
									break;
								}
							}

							// 判断是否超出读取数据的范围
							if(useLength < pos) {
								continue;
							}
							
							byte byteItem = buffer[pos];

							// 向下读取两行读到连续的两个 \r\n\r\n 后，field item 数据体为空
							if (byteItem == FIELD_SEPARATOR[0] 
									&& buffer[pos + 1] == FIELD_SEPARATOR[1]
									&& buffer[pos + 2] == FIELD_SEPARATOR[0]
									&& buffer[pos + 3] == FIELD_SEPARATOR[1]) {
								
								// 设置头部字段读取状态为 false
								fieldItemHeadRead = false;
								
								if (writeLength > 0) {
									
									byte[] headData = new byte[writeLength];

									System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

									fieldItemModule.getOutput().write(headData);
									
									fieldItemModule.getOutput().flush();
									
									fieldItemModule.getOutput().close();
									
									// TODO 思考是否要将 arrayBuffer 也置空

									writeLength = 0;
									
								}
								
								fieldItemList.add(fieldItemModule);
								
								// 退出循环
								break;
							}
							
							
							// 判断当前读取的内容和后面连续的内容
							if (byteItem == BOUNDARY_PREFIX[0] 
									&& buffer[pos + 1] == BOUNDARY_PREFIX[1]
									&& buffer[pos + 2] == BOUNDARY_PREFIX[2]
									&& buffer[pos + 3] == BOUNDARY_PREFIX[3]) {
								
								// 当前项目数据体读取完毕
								
								if (writeLength > 0) {
								
									byte[] headData = new byte[writeLength];

									System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

									fieldItemModule.getOutput().write(headData);
									
									fieldItemModule.getOutput().flush();
									
									fieldItemModule.getOutput().close();
									
									// TODO 思考是否要将 arrayBuffer 也置空

									writeLength = 0;
									
								}
								
								// 设置头部字段读取状态为 false
								fieldItemHeadRead = false;
								
								fieldItemList.add(fieldItemModule);
								
								// 创建新的表单字段对象
								fieldItemModule = new FieldItemModule();
								
								pos += 2;
								
								break;
								
							} 
							
							// 二进制数据内容不存在行的概念了，一直读取写入即可
							
							pos ++;
							
							// 存入缓存数组中
							arrayBuffer[writeLength] = byteItem;
							
							writeLength++;
							
							if(arrayBuffer.length == writeLength) {
								
								byte[] headData = new byte[writeLength - 1];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength - 1);

								fieldItemModule.getOutput().write(headData);
								
								writeLength = 0;
							}
						}
						
						// field item body end
						
					} // if fieldItemBodyRead end
				}
			} // if http 首部 end
			
			
			
			
			
			
			
			single = true;
			
			
			
			
			// buffer = new buffer();
			// pos = 0;
			// writeLength = 0;
			
			// 开始处理 HTTP Body 体
			// 一直处理到结束
			while ((readLength = bis.read(buffer, 0, buffer.length)) != -1) {
				
				int useLength = 0;
				writeLength = 0;
				for(;;pos++) {   // 处理 buffer
					
					System.out.println(pos);
					
					if(pos >= readLength) { // 退出
						pos = 0;
						useLength = 0;
						break;
					}else if(useLength > 0 && pos >= useLength) {
						pos = 0;
						useLength = 0;
						break; // 
					}
					
					byte byteItem = buffer[pos];
					
					if(!fieldItemHeadRead){ // 处理 POST 请求提交字段 Head
						
						// 首次处理预防措施
						if (pos > 0) { // 判断是否需要重新对其 buffer 缓冲区
							// 重新对其 buffer
							System.arraycopy(buffer, pos - 1, buffer, 0, readLength - pos);
							// 获取 buffer 剩余可用长度
							// useLength = buffer.length - pos - (buffer.length - useLength);
							// 获取 buffer 剩余可用长度
							useLength = (readLength - (pos - 1));
							// 重置 buffer 偏移量
							pos = 0;
						}
						
						// 判断是否是 field item 块开始，field item 的开始需要处理 field item 头部信息
						if(ByteUtil.arrayequals(buffer, bRelbBoundary, bRelbBoundary.length - 2)) {
							// boundary 相同过滤掉此行 // 判断最后 2 个字节是否是 \r\n
							pos += bRelbBoundary.length;
							
							// 判断是否超出读取数据的范围
							if(useLength < pos) {
								continue;
							}
							
							if(buffer[pos - 2] == DASH && buffer[pos - 1] == DASH) {
								break; // -- read Stream loop end // 已到流的末尾
							}
						}
						
						// 判断当前读取的内容和后面连续的内容 【\r\n\r\n】
						if (byteItem == FIELD_SEPARATOR[0] 
								&& buffer[pos + 1] == FIELD_SEPARATOR[1]
								&& buffer[pos + 2] == FIELD_SEPARATOR[0]
								&& buffer[pos + 3] == FIELD_SEPARATOR[1]) {
							
							// 头部信息读取完毕
							
							if (writeLength > 0) {
								
								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								// 本行读取结束
								fieldItemModule.getHeadItem().add(new String(headData));
								
								// TODO 思考是否要将 arrayBuffer 也置空

								writeLength = 0;

							}
							
							pos += 4;
							
							for (String fieldItemHead : fieldItemModule.getHeadItem()) {
								String[] heads = fieldItemHead.split(": ");
								fieldItemModule.getHeadMap().put(heads[0], heads[1]);
							}
							
							// 设置后续的内容类型
							fieldItemModule.setContentType(fieldItemModule.getHeadMap().get("Content-Type"));
							// 设置内容类型描述
							String[] contentDisposition = fieldItemModule.getHeadMap().get("Content-Disposition").split("; ");
							fieldItemModule.setContentDisposition(contentDisposition[0]);
							for (int i = 1; i < contentDisposition.length; i++) {
								
								String strContentDisposition = contentDisposition[i];
								int begin = strContentDisposition.indexOf("\"");
								int end = strContentDisposition.lastIndexOf("\"");
								
								
								// 文件名
								if (strContentDisposition.indexOf("filename") != -1) {
									if (begin != -1 && end != -1 && begin != end) {
										fieldItemModule.setFileName(strContentDisposition.substring(begin + 1, end));
									}
									// 字段名
								} else if (strContentDisposition.indexOf("name") != -1) {
									if (begin != -1 && end != -1 && begin != end) {
										fieldItemModule.setName(strContentDisposition.substring(begin + 1, end));
									}
								}
								// 字段值
							}
							
							fieldItemHeadRead = true;
							
							fieldItemBodyRead = false;

						} else if (byteItem == FIELD_SEPARATOR[0]
								&& buffer[pos + 1] == FIELD_SEPARATOR[1]) {
							
							// 本行读取完毕
							
							if (writeLength > 0) {
								
								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								// 本行读取结束
								fieldItemModule.getHeadItem().add(new String(headData));
								
								// TODO 思考是否要将 arrayBuffer 也置空

								writeLength = 0;

							}

							pos += 2; // buffer[pos + 1] == \n 下次循环跳过 \n
							
						}
					}
					
					if(!fieldItemBodyRead) { // 处理 POST 请求提交字段 Body
						
						// 向下读取两行读到连续的两个 \r\n\r\n 后，field item 数据体为空
						if (byteItem == FIELD_SEPARATOR[0] 
								&& buffer[pos + 1] == FIELD_SEPARATOR[1]
								&& buffer[pos + 2] == FIELD_SEPARATOR[0]
								&& buffer[pos + 3] == FIELD_SEPARATOR[1]) {
							
							if (writeLength > 0) {
								
								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								fieldItemModule.getOutput().write(headData);
								
								fieldItemModule.getOutput().flush();
								
								fieldItemModule.getOutput().close();
								
								// TODO 思考是否要将 arrayBuffer 也置空

								writeLength = 0;
								
							}
							
							fieldItemList.add(fieldItemModule);
							
							fieldItemBodyRead = true;
							
							fieldItemHeadRead = false;
						}
						
						// 判断当前读取的内容和后面连续的内容
						if (byteItem == BOUNDARY_PREFIX[0] 
								&& buffer[pos + 1] == BOUNDARY_PREFIX[1]
								&& buffer[pos + 2] == BOUNDARY_PREFIX[2]
								&& buffer[pos + 3] == BOUNDARY_PREFIX[3]) {
							
							// 当前项目数据体读取完毕
							
							if (writeLength > 0) {
							
								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								fieldItemModule.getOutput().write(headData);
								
								fieldItemModule.getOutput().flush();
								
								fieldItemModule.getOutput().close();
								
								// TODO 思考是否要将 arrayBuffer 也置空

								writeLength = 0;
								
							}
							
							fieldItemList.add(fieldItemModule);
							
							// 创建新的表单字段对象
							fieldItemModule = new FieldItemModule();
							
							pos += 2;
							
							// 当前项目数据体读取完毕
							fieldItemBodyRead = true;
							
							// 设置头部字段读取状态为 false
							fieldItemHeadRead = false;
						}
						
						// 赋值
						arrayBuffer[writeLength] = byteItem;
						
						writeLength ++;
						
						if(arrayBuffer.length == writeLength) {
							
							fieldItemModule.getOutput().write(arrayBuffer);
							
							writeLength = 0; // 第一次完成后下一次添加，少了 2 个字节
						}else if(pos >= readLength){
							if (writeLength > 0) {
								
								byte[] headData = new byte[writeLength];

								System.arraycopy(arrayBuffer, 0, headData, 0, writeLength);

								fieldItemModule.getOutput().write(headData);
								
								fieldItemModule.getOutput().flush();
								
								fieldItemModule.getOutput().close();
								
								// TODO 思考是否要将 arrayBuffer 也置空
								writeLength = 0;
							}
						}
					}
				}// -- 处理 POST 请求提交字段 end
			}
			
			for (String head : headPart) {
				System.out.println(head);
			}
			
			for (FieldItemModule itemModule : fieldItemList) {
				for (String head : itemModule.getHeadItem()) {
					System.out.println(head);
				}
			}
			
//			for (String head : headPart) {
//				String[] arryHead = head.split(": ");
//				if(arryHead.length == 1) {
//					System.out.println(headMap.get("protocol"));
//				}else if(arryHead.length == 2) {
//					System.out.println(headMap.get(arryHead[0]));
//				}
//			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
