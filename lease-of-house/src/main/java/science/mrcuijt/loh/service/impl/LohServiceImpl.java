/**
 * 
 */
package science.mrcuijt.loh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;

/**
 * @author Administrator
 *
 */
public class LohServiceImpl implements LohService {

	private LohDao lohDao = new LohDaoImpl();

	public LohDao getLohDao() {
		return lohDao;
	}

	public void setLohDao(LohDao lohDao) {
		this.lohDao = lohDao;
	}

	/**
	 * 用户注册的业务逻辑接口
	 * 
	 * @param userInfo
	 * @param loginInfo
	 * @return
	 */
	@Override
	public boolean userRegister(UserInfo userInfo, LoginInfo loginInfo) {

		// 返回函数值
		return lohDao.saveUserInfoAndLoginInfo(userInfo, loginInfo);
	}

	/**
	 * 用户登录的业务逻辑接口
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@Override
	public LoginInfo userLogin(String userName, String password) {

		LoginInfo info = lohDao.findLoginInfo(userName, password);

		// 返回函数值
		return info;
	}

	/**
	 * 验证用户是否存在的业务逻辑接口
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public boolean existsUser(String userName) {

		LoginInfo loginInfo = lohDao.findLoginInfoByLoginName(userName);

		if (loginInfo != null) {

			// 返回函数值
			return true;
		}

		// 返回函数值
		return false;
	}

	/**
	 * 查询登录信息的业务逻辑接口
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public LoginInfo findLoginInfo(String userName) {

		// 返回函数值
		return lohDao.findLoginInfoByLoginName(userName);
	}

	/**
	 * 更新登录信息的业务逻辑接口
	 * 
	 * @param loginInfo
	 * @return
	 */
	@Override
	public boolean updateLoginInfo(LoginInfo loginInfo) {

		return lohDao.updateLoginInfo(loginInfo);
	}

	/**
	 * 添加房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	@Override
	public boolean addLohHouseInfo(LohHouseInfo lohHouseInfo) {

		return lohDao.addLohHouseInfo(lohHouseInfo);
	}
	
	/**
	 * 添加房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @param lohFileInfoList
	 */
	@Override
	public boolean addLohHouseInfo(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList) {
		
		if(lohFileInfoList == null || lohFileInfoList.size() == 0) {
			return lohDao.addLohHouseInfo(lohHouseInfo);
		}
		
		return lohDao.addLohHouseInfo(lohHouseInfo, lohFileInfoList);
	}
	
	/**
	 * 根据房屋信息主键查询房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public LohHouseInfo findLohHouseInfoByPrimaryKey(Integer lohHouseInfoId) {

		return lohDao.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
	}

	/**
	 * 查询用户信息的业务逻辑接口
	 * 
	 * @param userInfoId
	 * @return
	 */
	@Override
	public UserInfo findUserInfoByPrimaryKey(Integer userInfoId) {

		return lohDao.findUserInfoByPrimaryKey(userInfoId);
	}

	/**
	 * 查询用户发布的房屋信息列表的业务逻辑接口（分页查询）
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	@Override
	public Map<String, Object> queryHouseInfoPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam) {

		// Map<String, Object> queryPagination = new HashMap<String, Object>();

		// 查询总记录数

		// 分页查询

		return lohDao.queryHouseInfoPagination(pageIndex, pageSize, queryParam);
	}

	/**
	 * 查询全部房屋类型列表的业务逻辑接口
	 * 
	 * @return
	 */
	@Override
	public List<LohHouseType> findAllLohHouseType() {
		return lohDao.findAllLohHouseType();
	}

	/**
	 * 根据地区级别查询地区信息的业务逻辑接口
	 * 
	 * @param regionLevel
	 * @return
	 */
	@Override
	public List<RegionInfo> findRegionInfoByLevel(Integer regionLevel) {

		return lohDao.findRegionInfoByLevel(regionLevel);
	}

	/**
	 * 根据父级地区Id查询对应的子集地区信息的业务逻辑接口
	 * 
	 * @param parentRegionId
	 * @return
	 */
	@Override
	public List<RegionInfo> findregionInfoByParentRegionId(Integer parentRegionId) {

		return lohDao.findregionInfoByParentRegionId(parentRegionId);
	}

	/**
	 * 验证房屋类型是否存在的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public boolean existsLohHouseTypeByPrimaryKey(Integer lohHouseTypeId) {
		LohHouseType lohHouseType = lohDao.findLohHouseTypeByPrimaryKey(lohHouseTypeId);
		return lohHouseType == null ? false : true;
	}
	
	/**
	 * 根据房屋类型Id查询房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId) {
		LohHouseType lohHouseType = lohDao.findLohHouseTypeByPrimaryKey(lohHouseTypeId);
		return lohHouseType;
	}

	/**
	 * 根据房屋信息 Id 查询房屋文件信息的业务逻辑接口
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public List<LohFileInfo> findLohFileInfoByLohHouseInfoId(Integer lohHouseInfoId) {

		return lohDao.findLohFileInfoByLohHouseInfoId(lohHouseInfoId);
	}

}
