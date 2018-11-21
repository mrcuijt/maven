/**
 * 
 */
package module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class FieldItemModule {

	// Field Item 首部
	private List<String> headItem = new ArrayList<String>();

	// Field Item 首部解析
	private Map<String, String> headMap = new HashMap<String, String>();

	// 临时文件
	private File file = new File(System.currentTimeMillis() + ".tmp");

	// 输入流
	private InputStream input = null;

	// 输出流
	private OutputStream output = null;

	// 内容类型
	private String contentType = null;

	// 内容描述
	private String contentDisposition = null;

	// 字段名称
	private String name = null;

	// 字段值
	private String value = null;

	// 文件名
	private String fileName = null;

	public byte[] toByteArray() {

		return null;
	}

	public String toStringByteArray() {

		return null;
	}

	public List<String> getHeadItem() {
		return headItem;
	}

	public void setHeadItem(List<String> headItem) {
		this.headItem = headItem;
	}

	public Map<String, String> getHeadMap() {
		return headMap;
	}

	public void setHeadMap(Map<String, String> headMap) {
		this.headMap = headMap;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public OutputStream getOutput() {
		if(output == null) {
			try {
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
