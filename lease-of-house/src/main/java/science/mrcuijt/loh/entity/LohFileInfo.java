package science.mrcuijt.loh.entity;

import java.util.Date;

public class LohFileInfo {
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
	private String lohHouseInfoId;

	/**
	 *
	 */
	private Integer lohFileTypeId;

	/**
	 *
	 */
	private String fileTitle;

	/**
	 *
	 */
	private String fileLink;

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
	public String getLohHouseInfoId() {
		return lohHouseInfoId;
	}

	/**
	 *
	 */
	public void setLohHouseInfoId(String lohHouseInfoId) {
		this.lohHouseInfoId = lohHouseInfoId;
	}

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
	public String getFileTitle() {
		return fileTitle;
	}

	/**
	 *
	 */
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	/**
	 *
	 */
	public String getFileLink() {
		return fileLink;
	}

	/**
	 *
	 */
	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}
}