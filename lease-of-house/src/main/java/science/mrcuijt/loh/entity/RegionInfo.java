package science.mrcuijt.loh.entity;

import java.util.Date;

public class RegionInfo {
	/**
	 *
	 */
	private Integer regionInfoId;

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
	private String regionCode;

	/**
	 *
	 */
	private String regionName;

	/**
	 *
	 */
	private Integer regionLevel;

	/**
	 *
	 */
	private Integer parentRegionId;

	/**
	 *
	 */
	private Integer lohHouseInfoId;

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
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * 
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 
	 */
	public Integer getRegionLevel() {
		return regionLevel;
	}

	/**
	 * 
	 */
	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}

	/**
	 * 
	 */
	public Integer getParentRegionId() {
		return parentRegionId;
	}

	/**
	 * 
	 */
	public void setParentRegionId(Integer parentRegionId) {
		this.parentRegionId = parentRegionId;
	}

	/**
	 * 
	 */
	public Integer getLohHouseInfoId() {
		return lohHouseInfoId;
	}

	/**
	 * 
	 */
	public void setLohHouseInfoId(Integer lohHouseInfoId) {
		this.lohHouseInfoId = lohHouseInfoId;
	}
}