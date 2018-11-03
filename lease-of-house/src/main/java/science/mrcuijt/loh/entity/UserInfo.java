package science.mrcuijt.loh.entity;

import java.util.Date;

public class UserInfo {
	/**
	 *
	 */
	private Integer userInfoId;

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
	private String userName;

	/**
	 *
	 */
	private Date bornDate;

	/**
	 *
	 */
	private Integer regionInfoId;

	/**
	 *
	 */
	private String cellPhone;

	/**
	 *
	 */
	private String detailedInformation;

	/**
	 * 
	 */
	public Integer getUserInfoId() {
		return userInfoId;
	}

	/**
	 *
	 */
	public void setUserInfoId(Integer userInfoId) {
		this.userInfoId = userInfoId;
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
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 */
	public Date getBornDate() {
		return bornDate;
	}

	/**
	 * 
	 */
	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	/**
	 * 
	 */
	public Integer getRegionInfoId() {
		return regionInfoId;
	}

	/**
	 * 
	 */
	public void setRegionInfoId(Integer regionInfoId) {
		this.regionInfoId = regionInfoId;
	}

	/**
	 * 
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * 
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * 
	 */
	public String getDetailedInformation() {
		return detailedInformation;
	}

	/**
	 * 
	 */
	public void setDetailedInformation(String detailedInformation) {
		this.detailedInformation = detailedInformation;
	}
}