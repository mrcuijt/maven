/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.LohHouseViewHistory;
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
	 * @throws SQLException
	 */
	public abstract boolean updateLoginInfo(LoginInfo loginInfo) throws SQLException;

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
	 * 删除房屋信息
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	public abstract boolean deleteLohHouseInfoByPrimaryKey(Integer lohHouseInfoId);

	/**
	 * 用户发布房屋信息（LohHouseInfo）的分页查询方法
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
	 * 根据地区级别查询地区信息
	 *
	 * @param regionLevel
	 * @param limit
	 * @return
	 */
	public abstract List<RegionInfo> findRegionInfoByLevel(Integer regionLevel, Integer limit);

	/**
	 * 根据父级地区Id查询对应的子集地区信息
	 * 
	 * @param parentRegionId
	 * @return
	 */
	public abstract List<RegionInfo> findRegionInfoByParentRegionId(Integer parentRegionId);

	/**
	 * 根据地区信息表主键查询地区信息表记录
	 * 
	 * @param regionInfoId
	 * @return
	 */
	public abstract RegionInfo findRegionInfoByPrimaryKey(Integer regionInfoId);

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
	 * @param lohFileInfoList
	 * @return
	 */
	public abstract boolean deleteFileInfoList(List<LohFileInfo> lohFileInfoList);

	/**
	 * 添加房屋文件信息列表
	 * 
	 * @param lohHouseInfo
	 * @param lohFileInfoList
	 * @return
	 */
	public abstract boolean addFileInfoList(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList);

	/**
	 * 分页查询房屋信息浏览记录
	 *
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	public abstract Map<String, Object> queryLohHouseViewHistoryPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam);

	/**
	 * 查询指定 id 集合的房屋信息列表
	 *
	 * @param strIds
	 * @return
	 */
	public abstract List<LohHouseInfo> queryHouseInfoListByIds(String strIds);

	/**
	 * 根据房屋信息id与用户id查询房屋信息浏览记录
	 *
	 * @param lohHouseInfoId
	 * @param userInfoId
	 * @return
	 */
	public abstract LohHouseViewHistory findLohHouseViewHistoryByLohHouseInfoIdAndUserInfoId(Integer lohHouseInfoId,
			Integer userInfoId);

	/**
	 * 添加房屋信息浏览记录
	 *
	 * @param lohHouseViewHistory
	 * @return
	 */
	public abstract boolean addLohHouseViewHistory(LohHouseViewHistory lohHouseViewHistory);

	/**
	 * 更新房屋信息浏览记录
	 *
	 * @param lohHouseViewHistory
	 * @return
	 */
	public abstract boolean updateLohHouseViewHistoryByPrimaryKey(LohHouseViewHistory lohHouseViewHistory);

	/**
	 * 查询房屋信息浏览记录
	 *
	 * @param lohHoueseViewHistoryId
	 * @return
	 */
	public abstract LohHouseViewHistory findLohHouseViewHistoryByPrimaryKey(Integer lohHoueseViewHistoryId);

	/**
	 * 根据房屋信息浏览记录id与用户id查询房屋信息浏览记录
	 *
	 * @param lohHouseInfoId
	 * @param userInfoId
	 * @return
	 */
	public abstract LohHouseViewHistory findLohHouseViewHistoryByLohHouseViewHistoryIdAndUserInfoId(
			Integer lohHoueseViewHistoryId, Integer userInfoId);

	/**
	 * 根据主键删除房屋信息浏览记录
	 *
	 * @param lohHouseViewHistoryId
	 * @return
	 */
	public abstract boolean deleteLohHouseViewHistoryByPrimaryKey(Integer lohHouseViewHistoryId);

}
