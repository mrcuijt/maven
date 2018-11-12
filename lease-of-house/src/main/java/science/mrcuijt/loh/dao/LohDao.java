/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.util.List;
import java.util.Map;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;

/**
 * @author Administrator
 *
 */
public interface LohDao {

	/**
	 * 保存用户信息和登录信息的方法
	 * 
	 * @param userInfo
	 * @param loginInfo
	 * @return
	 */
	public abstract boolean saveUserInfoAndLoginInfo(UserInfo userInfo, LoginInfo loginInfo);

	/**
	 * 找寻登录信息的方法
	 * 
	 * @param loginName
	 * @param loginPassword
	 * @return
	 */
	public abstract LoginInfo findLoginInfo(String loginName, String loginPassword);

	/**
	 * 找寻用户信息的方法
	 * 
	 * @param userInfoId
	 * @return
	 */
	public abstract UserInfo findUserInfoByPrimaryKey(Integer userInfoId);

	/**
	 * 根据 loginName 找寻用户信息的方法
	 * 
	 * @param userName
	 * @return
	 */
	public abstract LoginInfo findLoginInfoByLoginName(String userName);

	/**
	 * 更新 LoginInfo 的方法
	 * 
	 * @param loginInfo
	 * @return
	 */
	public abstract boolean updateLoginInfo(LoginInfo loginInfo);

	/**
	 * 添加 LohHouseInfo 的方法
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	public abstract boolean addLohHouseInfo(LohHouseInfo lohHouseInfo);

	/**
	 * 添加房屋信息和房屋文件信息的信息
	 * 
	 * @param lohHouseInfo
	 * @param lohFileInfoList
	 */
	public abstract boolean addLohHouseInfo(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList);
	
	/**
	 * 根据房屋信息 Id 更新房屋信息
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	public abstract boolean updateHouseInfoResult(LohHouseInfo lohHouseInfo);
	
	/**
	 * 用户发布法务信息（LohHouseInfo）的分页查询方法
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	public abstract Map<String, Object> queryHouseInfoPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam);

	/**
	 * 查询所有房屋类型记录
	 * 
	 * @return
	 */
	public abstract List<LohHouseType> findAllLohHouseType();

	/**
	 * 根据地区级别查询地区信息
	 * 
	 * @param regionLevel
	 * @return
	 */
	public abstract List<RegionInfo> findRegionInfoByLevel(Integer regionLevel);

	/**
	 * 根据父级地区Id查询对应的子集地区信息
	 * 
	 * @param parentRegionId
	 * @return
	 */
	public abstract List<RegionInfo> findregionInfoByParentRegionId(Integer parentRegionId);

	/**
	 * 根据主键查询查询房屋类型信息
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	public abstract LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId);

	/**
	 * 根据房屋信息主键查询房屋信息
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	public abstract LohHouseInfo findLohHouseInfoByPrimaryKey(Integer lohHouseInfoId);

	/**
	 * 根据房屋信息 Id 查询房屋文件信息
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	public abstract List<LohFileInfo> findLohFileInfoByLohHouseInfoId(Integer lohHouseInfoId);

	/**
	 * 删除房屋文件信息列表
	 * 
	 * @param deleteLohFileInfoList
	 * @return
	 */
	public abstract boolean deleteFileInfoList(List<LohFileInfo> deleteLohFileInfoList);

	/**
	 * 添加房屋文件信息列表
	 * 
	 * @param lohHouseInfo 
	 * @param lohFileInfoList
	 * @return
	 */
	public abstract boolean addFileInfoList(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList);

}
