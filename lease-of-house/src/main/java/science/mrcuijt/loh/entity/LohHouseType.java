package science.mrcuijt.loh.entity;

import java.util.Date;

public class LohHouseType {
	/**
	 *
	 */
	private Integer lohHouseTypeId;

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
	private String houseType;

	/**
	 *
	 */
	public Integer getLohHouseTypeId() {
		return lohHouseTypeId;
	}

	/**
	 *
	 */
	public void setLohHouseTypeId(Integer lohHouseTypeId) {
		this.lohHouseTypeId = lohHouseTypeId;
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
	public String getHouseType() {
		return houseType;
	}

	/**
	 * 
	 */
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
}