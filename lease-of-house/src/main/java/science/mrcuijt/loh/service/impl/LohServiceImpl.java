/**
 * 
 */
package science.mrcuijt.loh.service.impl;

import java.util.HashMap;
import java.util.Map;

import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
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

}
