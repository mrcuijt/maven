/**
 * 
 * 
 * 创建时间：2017-10-29 下午11:23:46
 * @author：崔旧涛
 */
package science.mrcuijt.hibernate.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * 
 * 
 * 创建时间：2017-10-29 下午11:23:46
 * @author 崔旧涛
 * 
 */
@Entity
@Table(name = "file_upload_list")
public class FileUploadList implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id = null;

	@Column(name = "file_name")
	@Type(type = "string")
	private String fileName = null;

	@Column(name = "file_meta_type")
	@Type(type = "string")
	private String fileMetaType = null;

	@Column(name = "file_size")
	@Type(type = "string")
	private String fileSize = null;

	@Column(name = "file_path")
	@Type(type = "string")
	private String filePath = null;

	@Column(name = "request_time")
//	@Type(type = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTime = null;

	@Column(name = "upload_time")
//	@Type(type = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime = null;

	@Column(name = "finished_time")
//	@Type(type = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedTime = null;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		Id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileMetaType
	 */
	public String getFileMetaType() {
		return fileMetaType;
	}

	/**
	 * @param fileMetaType the fileMetaType to set
	 */
	public void setFileMetaType(String fileMetaType) {
		this.fileMetaType = fileMetaType;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the requestTime
	 */
	public Date getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @return the uploadTime
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the finishedTime
	 */
	public Date getFinishedTime() {
		return finishedTime;
	}

	/**
	 * @param finishedTime the finishedTime to set
	 */
	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

}
