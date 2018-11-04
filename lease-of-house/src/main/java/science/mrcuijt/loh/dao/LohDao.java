/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.util.Map;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
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
	 * 用户发布法务信息（LohHouseInfo）的分页查询方法
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	public abstract Map<String, Object> queryHouseInfoPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam);

}
