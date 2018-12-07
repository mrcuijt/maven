/**
 * 
 */
package science.mrcuijt.loh.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import science.mrcuijt.loh.comm.LohFileType;
import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.UserInfo;

/**
 * @author Administrator
 *
 */
public class JDBCUtilTest {

	Logger log = LoggerFactory.getLogger(JDBCUtilTest.class);

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
	public void updateLoginInfo() throws SQLException {

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

	/**
	 * alibaba Druid 数据库连接池 PrepareStatement Cache，
	 * 异常缓存 GeneratedKeys 测试
	 */
	@Test
	public void testGeneratedKeys() {

		// 添加 LohFileInfo 成功获取 GenerateKey 测试
		addLohFileInfoTestSuccess();

		System.out.println("||");
		System.out.println("||");
		System.out.println("||");

		// 添加 LohFileInfo 失败获取 GenerateKey 测试
		addLohFileInfoTestError();

	}

	@Test
	public void addLohFileInfoTestSuccess() {

		boolean addFileInfoListResult = false;

		StringBuffer strbAddLohFileInfo = new StringBuffer();
		strbAddLohFileInfo.append(" INSERT INTO loh_file_info ");
		strbAddLohFileInfo.append(" ( ");
		strbAddLohFileInfo.append(" gmt_create , ");
		strbAddLohFileInfo.append(" gmt_modified , ");
		strbAddLohFileInfo.append(" loh_house_info_id , ");
		strbAddLohFileInfo.append(" loh_file_type_id , ");
		strbAddLohFileInfo.append(" file_title , ");
		strbAddLohFileInfo.append(" file_link ");
		strbAddLohFileInfo.append(" ) ");
		strbAddLohFileInfo.append(" VALUES ( ?, ?, ?, ?, ?,  ? ) ");

		String sql = strbAddLohFileInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			LohFileInfo lohFileInfo = new LohFileInfo();

			// 设置房屋文件信息记录所属的房屋信息
			lohFileInfo.setLohHouseInfoId(512);
			lohFileInfo.setGmtCreate(new Date());
			lohFileInfo.setGmtModified(new Date());
			lohFileInfo.setLohFileTypeId(LohFileType.LohHousePicture);

			ps.setTimestamp(1, new Timestamp(lohFileInfo.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(lohFileInfo.getGmtModified().getTime()));
			ps.setInt(3, lohFileInfo.getLohHouseInfoId());
			ps.setInt(4, lohFileInfo.getLohFileTypeId());
			ps.setString(5, lohFileInfo.getFileTitle());
			ps.setString(6, lohFileInfo.getFileLink());

			ps.addBatch();

			// 批量执行所有的 SQL 语句，获取所有执行结果的影响行数
			int[] addLohFileInfoCount = ps.executeBatch();

			log.info("{}", addLohFileInfoCount);

			// 获取自动增长列
			rs = ps.getGeneratedKeys();

			while (rs.next()) {
				log.debug("{}", rs);
			}

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {

			JDBCUtil.closeAll(rs, ps, conn);
		}

	}

	@Test
	public void addLohFileInfoTestError() {

		boolean addFileInfoListResult = false;

		StringBuffer strbAddLohFileInfo = new StringBuffer();
		strbAddLohFileInfo.append(" INSERT INTO loh_file_info ");
		strbAddLohFileInfo.append(" ( ");
		strbAddLohFileInfo.append(" gmt_create , ");
		strbAddLohFileInfo.append(" gmt_modified , ");
		strbAddLohFileInfo.append(" loh_house_info_id , ");
		strbAddLohFileInfo.append(" loh_file_type_id , ");
		strbAddLohFileInfo.append(" file_title , ");
		strbAddLohFileInfo.append(" file_link ");
		strbAddLohFileInfo.append(" ) ");
		strbAddLohFileInfo.append(" VALUES ( ?, ?, ?, ?, ?,  ? ) ");

		String sql = strbAddLohFileInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			// 批量执行所有的 SQL 语句，获取所有执行结果的影响行数
			int[] addLohFileInfoCount = ps.executeBatch();

			log.info("{}", addLohFileInfoCount);

			// 获取自动增长列
			rs = ps.getGeneratedKeys();

			while (rs.next()) {
				log.debug("{}", rs);
			}

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {

			JDBCUtil.closeAll(rs, ps, conn);
		}

	}

}
