/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Administrator
 * 
 */
public class TestForSocket implements Serializable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Socket socket = null;
		try {
			
			socket = new Socket("localhsot", 8989);// 客户端就已经可服务器建立通信了
			System.out.println("请输入要发送的信息：");
			String data = in.next();
			OutputStream os = socket.getOutputStream();// 获得输出流
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(data);
			System.out.println("发送成功");
			in.next();
		} catch (UnknownHostException e) {
			e.printStackTrace();
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
}
