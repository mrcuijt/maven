/**
 * 
 */
package science.mrcuijt.loh.service;

import java.util.List;

import science.mrcuijt.loh.entity.LohHouseInfo;
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

	/**
	 * 查询房屋类型的业务逻辑接口
	 * 
	 * @return
	 */
	List<LohHouseType> findLohHouseTypeList();

	/**
	 * 根据房屋类型Id查询房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId);

	/**
	 * 根据房屋类型Id查询房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	List<LohHouseInfo> findLohHouseInfoByLohHouseTypeId(Integer lohHouseTypeId);

}
