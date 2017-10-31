/**
 * 
 * 
 * 创建时间：2017-10-29 下午6:17:54
 * @author：崔旧涛
 */
package science.mrcuijt.struts2.action.comm;

import java.beans.Encoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import science.mrcuijt.struts2.util.GlobalFiled;

/**
 * 
 * 
 * 创建时间：2017-10-29 下午6:17:54
 * @author 崔旧涛
 * 
 */
@ParentPackage("comm")
@Namespace("/comm")
@Action(value = "fileUpload", results = {
		// ,params={"encode","true"}
		@Result(name = "success", type = "redirect", location = "/html/comm/file_upload_success.jsp?message=${message}"),
		@Result(name = "error", type = "dispatcher", location = "/html/error/file_upload_error.jsp") })
public class FileUploadAction implements Serializable {

	private String fileFileName = null;

	private File file = null;

	private InputStream inputStream = null;

	private byte[] buffer = new byte[1024 * 2];

	private String message = null;

	/**
	 * 
	 * 
	 * 
	 * 开发时间：2017-10-29 下午6:18:17
	 * @author：崔旧涛
	 * @return
	 */
	public String execute() {

		if(fileFileName == null || fileFileName.trim().length() == 0 || file == null){

			message = "请选择文件后重新进行上传！";
			
			return "error";
		}
		
		String savePath = ServletActionContext.getServletContext().getRealPath(
				GlobalFiled.UPLOADER_SPACE + "/" + fileFileName);

		File file = new File(savePath);

		if (file.exists()) {

			message = "文件已存在！";

			return "error";
		}

		if (!file.getParentFile().exists()) {

			file.getParentFile().mkdirs();

		}

		FileOutputStream fos = null;

		BufferedOutputStream bos = null;

		try {

			fos = new FileOutputStream(file);

			bos = new BufferedOutputStream(fos);

			int length = 0;

			inputStream = new FileInputStream(this.file);

			while ((length = inputStream.read(buffer, 0, buffer.length)) > 0) {

				bos.write(buffer, 0, length);
				System.out.println("字符流读取长度为：" + length);
			}

			bos.flush();

			message = "文件保存成功！";

			message += "<br />" + file.getAbsolutePath();

			message = URLEncoder.encode(message, "UTF-8");

		} catch (FileNotFoundException e) {
			message = "文件保存失败！" + e.getMessage();
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			message = "文件保存失败！" + e.getMessage();
			e.printStackTrace();
			return "error";
		} finally {

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return "success";
	}

	/**
	 * @return the fileFileName
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
