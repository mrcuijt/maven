/**
 * 
 */
package science.mrcuijt.loh.dbmgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.entity.LoginInfo;

/**
 * @author Administrator
 *
 */
public class DruidDataSourceTest {

	public DruidDataSource createDruidDataSource() {

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUsername("loh");
		dataSource.setPassword("loh");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/loh?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		dataSource.setUrl(
				"jdbc:mysql://localhost:3306/loh?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=CTT");

		return dataSource;
	}

	public Connection TestConnection() throws SQLException {

		DruidDataSource dataSource = createDruidDataSource();

		return dataSource.getConnection();
	}

	@Test
	public void testDruidPooledPreparedStatement() {

		try {
			findLoginInfoByLoginName("%\'" + "' or 1 = 1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LoginInfo findLoginInfoByLoginName(String loginName) throws SQLException {

		LoginInfo loginInfo = null;

		StringBuffer strbFindLoginInfo = new StringBuffer();
		strbFindLoginInfo.append(" SELECT  ");
		strbFindLoginInfo.append(" login_info_id ,");
		strbFindLoginInfo.append(" gmt_create ,");
		strbFindLoginInfo.append(" gmt_modified ,");
		strbFindLoginInfo.append(" login_account , ");
		strbFindLoginInfo.append(" login_password ,");
		strbFindLoginInfo.append(" user_info_id , ");
		strbFindLoginInfo.append(" current_login_time , ");
		strbFindLoginInfo.append(" last_login_time , ");
		strbFindLoginInfo.append(" login_ip ");
		strbFindLoginInfo.append(" FROM login_info ");
		strbFindLoginInfo.append(" WHERE login_account like ? ");

		String findLoginInfoSQL = strbFindLoginInfo.toString();

		Connection conn = TestConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = conn.prepareStatement(findLoginInfoSQL);

			ps.setString(1, loginName);

			rs = ps.executeQuery();

			if (ps instanceof DruidPooledPreparedStatement) {
				DruidPooledPreparedStatement dpps = (DruidPooledPreparedStatement) ps;
				System.out.println(dpps.getSql());
				System.out.println(dpps);
				System.out.println(ps);
			}

			while (rs.next()) {

				loginInfo = new LoginInfo();

				loginInfo.setLoginInfoId(rs.getInt("login_info_id"));
				loginInfo.setGmtCreate(rs.getTimestamp("gmt_create"));
				loginInfo.setGmtModified(rs.getTimestamp("gmt_modified"));
				loginInfo.setLoginAccount(rs.getString("login_account"));
				loginInfo.setLoginPassword(rs.getString("login_password"));
				loginInfo.setUserInfoId(rs.getInt("user_info_id"));
				loginInfo.setCurrentLoginTime(rs.getTimestamp("current_login_time"));
				loginInfo.setLastLoginTime(rs.getTimestamp("last_login_time"));
				loginInfo.setLoginIp(rs.getString("login_ip"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			JDBCUtil.closeAll(rs, ps, conn);
		}

		return loginInfo;
	}

	@Test
	public void test() throws SQLException {

		DruidDataSourceTest test = new DruidDataSourceTest();

		LoginInfo loginInfo = test.findLoginInfoByLoginName("1");

		System.out.println(JSON.toJSONString(loginInfo));
	}
}
