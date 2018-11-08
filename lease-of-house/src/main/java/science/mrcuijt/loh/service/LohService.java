/**
 * 
 */
package science.mrcuijt.loh.service;

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
public interface LohService {

	/**
	 * 用户注册的业务逻辑接口
	 * 
	 * @param userInfo
	 * @param loginInfo
	 * @return
	 */
	public abstract boolean userRegister(UserInfo userInfo, LoginInfo loginInfo);

	/**
	 * 用户登录的业务逻辑接口
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public abstract LoginInfo userLogin(String userName, String password);

	/**
	 * 验证用户是否存在的业务逻辑接口
	 * 
	 * @param userName
	 * @return
	 */
	public abstract boolean existsUser(String userName);

	/**
	 * 查询登录信息的业务逻辑接口
	 * 
	 * @param userName
	 * @return
	 */
	public abstract LoginInfo findLoginInfo(String userName);

	/**
	 * 更新登录信息的业务逻辑接口
	 * 
	 * @param loginInfo
	 * @return
	 */
	public abstract boolean updateLoginInfo(LoginInfo loginInfo);

	/**
	 * 添加房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	public abstract boolean addLohHouseInfo(LohHouseInfo lohHouseInfo);

	/**
	 * 添加房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @param lohFileInfoList
	 */
	public abstract boolean addLohHouseInfo(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList);
	
	/**
	 * 根据房屋信息主键查询房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	public abstract LohHouseInfo findLohHouseInfoByPrimaryKey(Integer lohHouseInfoId);
	
	/**
	 * 查询用户信息的业务逻辑接口
	 * 
	 * @param userInfoId
	 * @return
	 */
	public abstract UserInfo findUserInfoByPrimaryKey(Integer userInfoId);

	/**
	 * 查询用户发布的房屋信息列表的业务逻辑接口（分页查询）
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	public abstract Map<String, Object> queryHouseInfoPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam);

	/**
	 * 查询全部房屋类型列表的业务逻辑接口
	 * 
	 * @return
	 */
	public abstract List<LohHouseType> findAllLohHouseType();

	/**
	 * 根据地区级别查询地区信息的业务逻辑接口
	 * 
	 * @param regionLevel
	 * @return
	 */
	public abstract List<RegionInfo> findRegionInfoByLevel(Integer regionLevel);

	/**
	 * 根据父级地区Id查询对应的子集地区信息的业务逻辑接口
	 * 
	 * @param parentRegionId
	 * @return
	 */
	public abstract List<RegionInfo> findregionInfoByParentRegionId(Integer parentRegionId);

	/**
	 * 验证房屋类型是否存在的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	public abstract boolean existsLohHouseTypeByPrimaryKey(Integer lohHouseTypeId);
	
	/**
	 * 根据房屋类型Id查询房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	public abstract LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId);

	/**
	 * 根据房屋信息 Id 查询房屋文件信息的业务逻辑接口
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	public abstract List<LohFileInfo> findLohFileInfoByLohHouseInfoId(Integer lohHouseInfoId);

}
