package science.mrcuijt.loh.dao.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.alibaba.druid.sql.SQLUtils.FormatOption;
import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;
import science.mrcuijt.loh.util.JDBCUtil;

public class DruidLogTest {

	private static final Logger LOG = LoggerFactory.getLogger(DruidLogTest.class);

	private static final DruidDataSource DRUID_DATA_SOURCE = new DruidDataSource();

	private static final String DRVIER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://localhost:3306/loh?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

	private static final String USER = "loh";

	private static final String PASSWORD = "loh";
	
	/**
	 * 
	 * 通常来说，只需要修改initialSize、minIdle、maxActive。
	 * 
	 * 如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。分库分表较多的数据库，建议配置为false。
	 * 
	 */
	static {
		
		LOG.trace("Load Druid JDBC DataSource");

		try {
			// 基本属性 url、user、password
			// Class.forName(DRVIER);
			DRUID_DATA_SOURCE.setUsername(USER);
			DRUID_DATA_SOURCE.setPassword(PASSWORD);
			DRUID_DATA_SOURCE.setUrl(URL);

			// 配置初始化大小、最小、最大
			DRUID_DATA_SOURCE.setInitialSize(5);
			DRUID_DATA_SOURCE.setMinIdle(10);
			DRUID_DATA_SOURCE.setMaxActive(20);

			// 配置获取连接等待超时时间
			DRUID_DATA_SOURCE.setMaxWait(60000);

			// 配置间隔多久才进行一次检测
			DRUID_DATA_SOURCE.setTimeBetweenEvictionRunsMillis(60000);

			// 配置一个连接在池中最小生存的时间，单位是毫秒
			DRUID_DATA_SOURCE.setMinEvictableIdleTimeMillis(300000);

			DRUID_DATA_SOURCE.setValidationQuery("SELECT 'x'");
			DRUID_DATA_SOURCE.setTestWhileIdle(true);
			DRUID_DATA_SOURCE.setTestOnBorrow(false);
			DRUID_DATA_SOURCE.setTestOnReturn(false);

			// 打开 PSCache ，并且指定每个连接上 PSCache 的大小
			DRUID_DATA_SOURCE.setPoolPreparedStatements(false); // MySQL 可以设置为 false
			DRUID_DATA_SOURCE.setMaxPoolPreparedStatementPerConnectionSize(20);

			// 连接泄露处理。Druid 提供了 RemoveAbandanded 相关配置，用来关闭长时间不使用的连接（例如忘记关闭时间）
			DRUID_DATA_SOURCE.setRemoveAbandoned(true);

			// 1800 秒，也就是 30 分钟
			DRUID_DATA_SOURCE.setRemoveAbandonedTimeout(1800);

			// 关闭 abanded 连接时输出的错误日志
			DRUID_DATA_SOURCE.setLogAbandoned(true);

			// 配置监控统计拦截的 filters ，监控统计 “stat”，防 SQL 注入：“wall”组合使用“stat,wall”
			DRUID_DATA_SOURCE.setFilters("stat,wall,slf4j");

			// 配置 SLF4J 日志输出
			List<Filter> filterList = DRUID_DATA_SOURCE.getProxyFilters();
			for (Filter filter : filterList) {
				if (filter instanceof Slf4jLogFilter) {
					Slf4jLogFilter slf4jLogFilter = (Slf4jLogFilter) filter;
					// 关闭返回结果集输出
					slf4jLogFilter.setResultSetLogEnabled(false);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error("Setting Druid DataSource Filters Error Stack", e);
		}
	}

	@Test
	public void testGetLogger() {
		
		try {
			
			DRUID_DATA_SOURCE.setFilters("stat，wall,slf4j");
			List<Filter> filterList = DRUID_DATA_SOURCE.getProxyFilters();
			for (Filter filter : filterList) {
				if(filter instanceof Slf4jLogFilter) {
					Slf4jLogFilter slf4jLogFilter = (Slf4jLogFilter) filter;
					slf4jLogFilter.setResultSetLogEnabled(false);
				}
			}
			
			LoginInfo loginInfo = findLoginInfoByLoginName("%");
			System.out.println(JSON.toJSONString(loginInfo, true));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void log4jTest() {

		// -Ddruid.log.stmt.executableSql=true
		Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
		slf4jLogFilter.setResultSetLogEnabled(false);
		slf4jLogFilter.setConnectionLogEnabled(false);
		slf4jLogFilter.setStatementParameterClearLogEnable(false);
		slf4jLogFilter.setStatementCreateAfterLogEnabled(false);
		slf4jLogFilter.setStatementCloseAfterLogEnabled(false);
		slf4jLogFilter.setStatementParameterSetLogEnabled(false);
		slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);
		
//	    slf4jLogFilter.setStatementExecuteAfterLogEnabled(false);
//		slf4jLogFilter.setStatementExecuteBatchAfterLogEnabled(false);
//		slf4jLogFilter.setStatementExecuteQueryAfterLogEnabled(false);
//		slf4jLogFilter.setStatementExecuteUpdateAfterLogEnabled(false);
		
//		List<Filter> filterLists = new ArrayList<>();
//		filterLists.add(slf4jLogFilter);
//		DRUID_DATA_SOURCE.setProxyFilters(filterLists);
	}

	@Test
	public void testDruidPooledPreparedStatement() {

		try {
//			log4jTest();
			LoginInfo loginInfo = findLoginInfoByLoginName("%");
			System.out.println(JSON.toJSONString(loginInfo, true));
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
//				System.out.println(dpps.getSql());
//				System.out.println(dpps);
//				System.out.println(ps);
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
	public void findRegionInfo() {
		
		log4jTest();
		
		LohService lohService = new LohServiceImpl();
		
		lohService.findRegionInfoByLevel(1);
		
	}

}
