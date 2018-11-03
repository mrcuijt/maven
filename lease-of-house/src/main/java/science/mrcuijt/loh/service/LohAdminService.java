/**
 * 
 */
package science.mrcuijt.loh.service;

import science.mrcuijt.loh.entity.LohHouseType;

/**
 * @author Administrator
 *
 */
public interface LohAdminService {

	/**
	 * 查询房屋类型是否存在的业务逻辑接口
	 * 
	 * @param houseType
	 * 
	 * @return
	 */
	boolean findLohHouseTypeByHouseType(String houseType);

	/**
	 * 添加房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseType
	 * @return
	 */
	boolean addLohHouseType(LohHouseType lohHouseType);

}
