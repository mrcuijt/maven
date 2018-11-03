package science.mrcuijt.loh.entity;

import java.util.Date;

public class LohFileType {
	/**
	 *
	 */
	private Integer lohFileTypeId;

	/**
	 *
	 */
	private Integer lohFileInfoId;

	/**
	 *
	 */
	private Date gmtCreate;

	/**
	 *
	 */
	private Date gmtModified;

	/**
	 *
	 */
	private String fileType;

	/**
	 *
	 */
	public Integer getLohFileTypeId() {
		return lohFileTypeId;
	}

	/**
	 *
	 */
	public void setLohFileTypeId(Integer lohFileTypeId) {
		this.lohFileTypeId = lohFileTypeId;
	}

	/**
	 *
	 */
	public Integer getLohFileInfoId() {
		return lohFileInfoId;
	}

	/**
	 *
	 */
	public void setLohFileInfoId(Integer lohFileInfoId) {
		this.lohFileInfoId = lohFileInfoId;
	}

	/**
	 *
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 *
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 *
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 *
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 *
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 *
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}