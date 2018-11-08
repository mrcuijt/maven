/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.util.List;

import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;

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

	/**
	 * 查询所有的房屋类型
	 * 
	 * @return
	 */
	List<LohHouseType> findLohHouseTypeList();

	/**
	 * 根据房屋类型Id查询房屋类型
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId);

	/**
	 * 根据房屋类型Id查询房屋信息
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	List<LohHouseInfo> findLohHouseInfoByLohHouseTypeId(Integer lohHouseTypeId);

	/**
	 * 添加地区信息的方法
	 * 
	 * @param regionInfo
	 * @return
	 */
	boolean addRegionInfo(RegionInfo regionInfo);
	
	/**
	 * 根据地区信息表主键查询地区信息表记录
	 * 
	 * @param regionInfoId
	 * @return
	 */
	RegionInfo findRegionInfoByPrimaryKey(Integer regionInfoId);
	
	/**
	 * 根据地区信息表主键更新地区信息表记录
	 * 
	 * @param regionInfo
	 * @return
	 */
	boolean updateRegionInfoByPrimaryKey(RegionInfo regionInfo);
	
	/**
	 * 根据区域等级查询所有区域信息
	 * 
	 * @param level
	 * @return
	 */
	List<RegionInfo> findRegionInfoByLevel(Integer level);
}
