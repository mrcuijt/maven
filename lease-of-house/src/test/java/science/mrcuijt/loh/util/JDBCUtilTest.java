/**
 * 
 */
package science.mrcuijt.loh.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
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

		if (loginInfo == null)
			return;

		loginInfo.setCurrentLoginTime(new Date());

		boolean updateResult = lohDao.updateLoginInfo(loginInfo);

	}

	public void trunstackTable() {

		String sql = " TRUNCATE TABLE login_info ";

		JDBCUtil.truncateTable(sql);

		sql = " TRUNCATE TABLE user_info ";

		JDBCUtil.truncateTable(sql);

		sql = " TRUNCATE TABLE login_info ";
	}

	@Test
	public void testAddLohHouseInfo() {

		LohHouseInfo lohHouseInfo = new LohHouseInfo();

		lohHouseInfo.setGmtCreate(new Date());
		lohHouseInfo.setGmtModified(new Date());
//		lohHouseInfo.setHouseTitle(null);
		lohHouseInfo.setLohHouseTypeId(1);
//		lohHouseInfo.setHouseAddress(null);
//		lohHouseInfo.setContacts(null);
//		lohHouseInfo.setCellPhone(null);
		lohHouseInfo.setUserInfoId(1);
		lohHouseInfo.setPushDate(new Date());

		LohDao dao = new LohDaoImpl();
		dao.addLohHouseInfo(lohHouseInfo);
	}

	@Test
	public void testQueryLohHouseInfoPagination() {

		Integer pageIndex = null;
		Integer pageSize = null;

		if (pageIndex == null || pageIndex <= 0) {
			pageIndex = 1;
		}

		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}

		// 分页查询条件
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userInfoId", 3);

		LohDao dao = new LohDaoImpl();
		// 分页查询
		Map<String, Object> pagination = dao.queryHouseInfoPagination(pageIndex, pageSize, queryParam);
		
		System.out.println(JSON.toJSONString(pagination));
		
		Integer totalPage = (Integer) pagination.get("totalPage");
		
		for (int i = 2; i <= totalPage; i++) {
			pagination = dao.queryHouseInfoPagination(i, pageSize, queryParam);
			System.out.println(JSON.toJSONString(pagination));
		}
		
	}

}
