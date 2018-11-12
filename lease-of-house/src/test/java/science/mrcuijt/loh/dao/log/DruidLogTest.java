package science.mrcuijt.loh.dao.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.util.JDBCUtil;

public class DruidLogTest {

	private static final Logger LOG = Logger.getLogger(JDBCUtil.class);

	private static final DruidDataSource DRUID_DATA_SOURCE = new DruidDataSource();

	private static final String DRVIER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://localhost:3306/loh?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

	private static final String USER = "loh";

	private static final String PASSWORD = "loh";

	static {
		LOG.info("加载 MySQL JDBC 驱动");
		try {
			// Class.forName(DRVIER);
			DRUID_DATA_SOURCE.setUsername(USER);
			DRUID_DATA_SOURCE.setPassword(PASSWORD);
			DRUID_DATA_SOURCE.setUrl(URL);
		} catch (Exception e) {
			LOG.info("加载 MySQL JDBC 驱动出现异常", e);
			e.printStackTrace();
		}
	}

	@Test
	public void log4jTest() {

		// -Ddruid.log.stmt.executableSql=true
		Log4jFilter log4jFilter = new Log4jFilter();
		log4jFilter.setStatementExecutableSqlLogEnable(true);
		List<Filter> filterList = new ArrayList<>();
		filterList.add(log4jFilter);
		try {
			DRUID_DATA_SOURCE.setFilters("stat,log4j");
//			DRUID_DATA_SOURCE.setProxyFilters(filterList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDruidPooledPreparedStatement() {

		try {
			log4jTest();
			findLoginInfoByLoginName("%\'");
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

		Connection conn = DRUID_DATA_SOURCE.getConnection();
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

}
