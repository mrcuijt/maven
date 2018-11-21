package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Worker implements Runnable {

	private Socket socket = null;

	public Worker(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		System.out.println("-------------------------------------------------");
		System.out.println("");
		System.out.println("客户端接入成功！");
		// 获得服务器的信息
		System.out.println("ip:" + socket.getInetAddress());
		// 服务器开放的端口是服务器随机指定的
		System.out.println("客户端端口:" + socket.getLocalPort());// 端口
		System.out.println("");
		System.out.println("-------------------------------------------------");
		System.out.println("");
		System.out.println("");
		
		try {

			System.out.println("处理请求报文");
			System.out.println("");
			System.out.println("");
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// 读取请求头起始行
			String header = br.readLine();
			System.out.println(header);

			// 读取请求头部首
			String data = null;
			while (!(data = br.readLine()).equals("")) {
				System.out.println(data);
			}
			System.out.println();

			
			// 处理请求报文主体实体
			StringBuffer stringBuffer = new StringBuffer();
			int i = 0;
			if (header.startsWith("POST")) {

				// 读取 4 行
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				
				while (br.ready() && (i = br.read()) != -1)
				{
					stringBuffer.append( (char) i);
				}
			}

			System.out.println(stringBuffer);
			System.out.println();

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
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

}
