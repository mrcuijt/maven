package science.mrcuijt.loh.entity;

import java.math.BigDecimal;
import java.util.Date;

public class LohHouseInfo {
	/**
	 *
	 */
	private Integer lohHouseInfoId;

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
	private String houseTitle;

	/**
	 *
	 */
	private Integer userInfoId;

	/**
	 *
	 */
	private Integer lohHouseTypeId;

	/**
	 *
	 */
	private Integer regionInfoProvinceId;

	/**
	 *
	 */
	private Integer regionInfoCityId;
	
	/**
	 *
	 */
	private Integer regionInfoCountyId;

	/**
	 *
	 */
	private String houseAddress;

	/**
	 *
	 */
	private BigDecimal price;

	/**
	 *
	 */
	private Date pushDate;

	/**
	 *
	 */
	private String contacts;

	/**
	 *
	 */
	private String cellPhone;

	/**
	 *
	 */
	private String qrcodeLink;

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
	public String getHouseTitle() {
		return houseTitle;
	}

	/**
	 *
	 */
	public void setHouseTitle(String houseTitle) {
		this.houseTitle = houseTitle;
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
	public Integer getRegionInfoProvinceId() {
		return regionInfoProvinceId;
	}

	/**
	 *
	 */
	public void setRegionInfoProvinceId(Integer regionInfoProvinceId) {
		this.regionInfoProvinceId = regionInfoProvinceId;
	}

	/**
	 *
	 */
	public Integer getRegionInfoCityId() {
		return regionInfoCityId;
	}

	/**
	 *
	 */
	public void setRegionInfoCityId(Integer regionInfoCityId) {
		this.regionInfoCityId = regionInfoCityId;
	}

	/**
	 *
	 */
	public Integer getRegionInfoCountyId() {
		return regionInfoCountyId;
	}

	/**
	 *
	 */
	public void setRegionInfoCountyId(Integer regionInfoCountyId) {
		this.regionInfoCountyId = regionInfoCountyId;
	}

	/**
	 *
	 */
	public String getHouseAddress() {
		return houseAddress;
	}

	/**
	 *
	 */
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	/**
	 *
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 *
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 *
	 */
	public Date getPushDate() {
		return pushDate;
	}

	/**
	 *
	 */
	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	/**
	 *
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 *
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
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
	public String getQrcodeLink() {
		return qrcodeLink;
	}

	/**
	 * 
	 */
	public void setQrcodeLink(String qrcodeLink) {
		this.qrcodeLink = qrcodeLink;
	}
}