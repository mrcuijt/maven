package science.mrcuijt.loh.entity;

import java.util.Date;

public class LohHouseViewHistory {
	/**
	 *
	 */
	private Integer lohHouseViewHistoryId;

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
	private Integer lohHouseId;

	/**
	 *
	 */
	private Integer userInfoId;

	/**
	 *
	 */
	public Integer getLohHouseViewHistoryId() {
		return lohHouseViewHistoryId;
	}

	/**
	 *
	 */
	public void setLohHouseViewHistoryId(Integer lohHouseViewHistoryId) {
		this.lohHouseViewHistoryId = lohHouseViewHistoryId;
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
	public Integer getLohHouseId() {
		return lohHouseId;
	}

	/**
	 *
	 */
	public void setLohHouseId(Integer lohHouseId) {
		this.lohHouseId = lohHouseId;
	}

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
}