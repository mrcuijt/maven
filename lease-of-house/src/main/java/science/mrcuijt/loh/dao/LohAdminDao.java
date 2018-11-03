/**
 * 
 */
package science.mrcuijt.loh.dao;

import science.mrcuijt.loh.entity.LohHouseType;

/**
 * @author Administrator
 *
 */
public interface LohAdminDao {

	/**
	 * 根据房屋类型查询房屋类型记录
	 * 
	 * @param houseType 
	 * 
	 * @return
	 */
	LohHouseType findLohHouseTypeByHouseType(String houseType);

	/**
	 * 添加房屋类型
	 * 
	 * @param lohHouseType
	 * @return
	 */
	boolean addLohHouseType(LohHouseType lohHouseType);

}
