/**
 * 
 */
package science.mrcuijt.loh.service;

import science.mrcuijt.loh.entity.LoginInfo;
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
	public abstract boolean userRegister(UserInfo userInfo,LoginInfo loginInfo);
	
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

	
}
