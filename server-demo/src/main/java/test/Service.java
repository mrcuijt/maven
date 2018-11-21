/**
 * 
 */
package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Administrator
 * 
 */
public class Service implements Serializable {

	private static String encoding = "GBK";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8980;// 端口 // 为服务器指定端口
		ServerSocket serverSocket = null;
		Socket socket = null;
		byte[] buffer = new byte[1024 * 1024];
		try {
			serverSocket = new ServerSocket(port);// 创建服务器端的 Socket
			System.out.println("服务器启动成功！");
			// 等待返回客户端的信息
			socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他
			// 获得服务器的信息
			System.out.println("客户端接入成功！ip:" + socket.getInetAddress());
			// 服务器开放的端口是服务器随机指定的
			System.out.println("客户端接入成功！客户端端口:" + socket.getLocalPort());// 端口

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
			
			int length = -1;
//			while ((length = is.read(buffer, 0, buffer.length)) != -1) {
//				System.out.println(new String(buffer));
//			}
			
			// 读取请求头起始行
			String header = br.readLine();
			String data;
			int contentLength = 0;//服务器发送回来的消息长度
			while (!(data = br.readLine()).equals("")) {
				//如果有Content-Length消息头时取出
				if (data.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(data.split(":")[1].trim());
//					while ((length = is.read(buffer, 0, buffer.length)) != -1) {
//						System.out.println(new String(buffer));
//					}
				}
				System.out.println(data);
			}
			
//			BufferedInputStream bis = new BufferedInputStream(is);
//			
//			StringBuffer stringBuffer = new StringBuffer();
//			int i = 0;
//			int count = 0;
//			while (br.ready() && (i = br.read()) != -1)
//			{
//				count ++;
//				stringBuffer.append( (char) i);
//			}
//			System.out.println(count);
//
//			System.out.println(stringBuffer.toString());
//			int count = 0;
//			while (br.ready() && ((length = is.read(buffer, 0, buffer.length)) != -1)) {
//				count ++;
//				String n = new String(buffer);
//				System.out.println(n);
//			}
			
			if(header.startsWith("POST")) {
				// 读取所有服务器发送过来的请求参数头部信息
				while ((data = br.readLine()) != null) {
					// 如果有Content-Length消息头时取出
					System.out.println(data);
					
					if(data.endsWith("--")) {
						break;
					}
				}
			}
//			is.close();
			
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html; charset=utf-8");
            out.println("Server: MINISERVER");
            out.println("Keep-Alive:0");
            out.println("Connection:false");
            // this blank line signals the end of the headers
            out.println("");
            // Send the HTML page
            out.println("<H1>Welcome to the Mini Server</H1>");
            out.println();
            out.println("<form action=\"http://127.0.0.1:8980\" method=\"POST\" enctype=\"multipart/form-data\">");
            out.println("<input name=\"image\" type=\"file\" placeholder=\"请选择图片\" />");
            out.println("<input name=\"image\" type=\"file\" placeholder=\"请选择图片\" />");
            out.println("<input name=\"image\" type=\"file\" placeholder=\"请选择图片\" />");
            out.println("<input name=\"cellPhone\" type=\"text\" placeholder=\"请输入联系方式\"  value=\"\"/>");
            out.println("<button class=\"btn btn-default\" type=\"submit\">提交</button>");
            out.println("</form>");
            out.println();
            out.println("<form action=\"http://127.0.0.1:8980\" method=\"POST\" enctype=\"multipart/form-data\">");
            out.println("<input name=\"cellPhone\" type=\"text\" placeholder=\"请输入联系方式\"  value=\"\"/>");
            out.println("<button class=\"btn btn-default\" type=\"submit\">提交</button>");
            out.println("</form>");
            out.println();
            out.println("<form action=\"http://127.0.0.1:8980\" method=\"POST\">");
            out.println("<input name=\"cellPhone\" type=\"text\" placeholder=\"请输入联系方式\"  value=\"\"/>");
            out.println("<button class=\"btn btn-default\" type=\"submit\">提交</button>");
            out.println("</form>");
            out.println();
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js\"></script>");
            out.println("<button id=\"ajax\" class=\"btn btn-default\" type=\"button\">ajax</button>");
            out.println("<script>");
            out.println("$(\"#ajax\").bind(\"click\",function(){");
            out.println("$.post(\"\",{age:\"22\",name:\"张三\"},function(data){console.info(data);})");
            out.println("})");
            out.println("</script>");
            out.println();
            out.flush();
            out.close();
            is.close();
			
//			OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
//			StringBuffer sb = new StringBuffer();
//			sb.append("GET /HttpStream/gb2312.jsp HTTP/1.1\r\n");
//			sb.append("Host: localhost:8088\r\n");
//			sb.append("Connection: Keep-Alive\r\n");
			//注，这是关键的关键，忘了这里让我搞了半个小时。这里一定要一个回车换行，表示消息头完，不然服务器会等待
//			sb.append("\r\n");
//			osw.write(sb.toString());
//			osw.write("Success");
//			osw.flush();
//			osw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}               
		}
	}
	
	/*
	 * 这里我们自己模拟读取一行，因为如果使用API中的BufferedReader时，它是读取到一个回车换行后
	 * 才返回，否则如果没有读取，则一直阻塞，直接服务器超时自动关闭为止，如果此时还使用BufferedReader
	 * 来读时，因为读到最后一行时，最后一行后不会有回车换行符，所以就会等待。如果使用服务器发送回来的
	 * 消息头里的Content-Length来截取消息体，这样就不会阻塞
	 * 
	 * contentLe 参数 如果为0时，表示读头，读时我们还是一行一行的返回；如果不为0，表示读消息体，
	 * 时我们根据消息体的长度来读完消息体后，客户端自动关闭流，这样不用先到服务器超时来关闭。
	 */
	private static String readLine(InputStream is, int contentLe) throws IOException {
		ArrayList lineByteList = new ArrayList();
		byte readByte;
		int total = 0;
		if (contentLe != 0) {
			do {
				readByte = (byte) is.read();
				lineByteList.add(Byte.valueOf(readByte));
				total++;
			} while (total < contentLe);//消息体读还未读完
		} else {
			do {
				readByte = (byte) is.read();
				lineByteList.add(Byte.valueOf(readByte));
			} while (readByte != 10);
		}
 
		byte[] tmpByteArr = new byte[lineByteList.size()];
		for (int i = 0; i < lineByteList.size(); i++) {
			tmpByteArr[i] = ((Byte) lineByteList.get(i)).byteValue();
		}
		lineByteList.clear();
 
		return new String(tmpByteArr, encoding);
	}

}
