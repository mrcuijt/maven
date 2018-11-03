/**
 * 
 */
package science.mrcuijt.loh.util;

import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.UserInfo;

/**
 * @author Administrator
 *
 */
public class JDBCUtilTest {

	@Test
	public void saveTest() {

		UserInfo userInfo = new UserInfo();
		userInfo.setGmtCreate(new Date());
		userInfo.setGmtModified(new Date());
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setGmtCreate(new Date());
		loginInfo.setGmtModified(new Date());
		loginInfo.setLoginAccount("admin");
		loginInfo.setLoginPassword("admin1");

		LohDao dao = new LohDaoImpl();
		dao.saveUserInfoAndLoginInfo(userInfo, loginInfo);
		
	}

	@Test
	public void findUserInfoByPrimaryKey() {

		LohDao dao = new LohDaoImpl();

		UserInfo userinfo = dao.findUserInfoByPrimaryKey(13);

		System.out.println(JSON.toJSONString(userinfo, SerializerFeature.WriteMapNullValue));

	}

	@Test
	public void findLoginInfo() {

		LohDao dao = new LohDaoImpl();

		LoginInfo loginInfo = dao.findLoginInfo("admin", "admin1");

		System.out.println(JSON.toJSONString(loginInfo, SerializerFeature.WriteMapNullValue,
				SerializerFeature.UseISO8601DateFormat));

	}

	@Test
	public void dateTest() {

		Date date = new Date();

		System.out.println(JSON.toJSONString(date, SerializerFeature.UseISO8601DateFormat));
		
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		System.out.println(JSON.toJSONString(sqlDate, SerializerFeature.UseISO8601DateFormat));
	}
	
	@Test
	public void updateLoginInfo() {
		
		LohDao lohDao = new LohDaoImpl();
		
		LoginInfo loginInfo = lohDao.findLoginInfo("1", "1");
		
		if(loginInfo == null) return;
		
		loginInfo.setCurrentLoginTime(new Date());
		
		boolean updateResult = lohDao.updateLoginInfo(loginInfo);
		
	}

	@Test
	public void trunstackTable() {
		
		String sql = " TRUNCATE TABLE login_info ";
		
		JDBCUtil.truncateTable(sql);
		
		sql = " TRUNCATE TABLE user_info ";
		
		JDBCUtil.truncateTable(sql);
		
		sql = " TRUNCATE TABLE login_info ";
	}
}
