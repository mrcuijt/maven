/**
 * 
 */
package science.mrcuijt.loh.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;

/**
 * @author Administrator
 *
 */
public class JDBCUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JDBCUtil.class);

	private static final DruidDataSource DRUID_DATA_SOURCE = new DruidDataSource();

	private static final String DRVIER = "com.mysql.jdbc.Driver";

	// mysql 数据库连接url jdbc\:mysql\://localhost\:3306/test1
	// jdbc:oracle:thin:@localhost:1521:orcl
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
			// 由于设置缓存 PreparedStatement 导致执行 executeBatch() 时，再次执行相同的 SQL 语句时 getGeneratedKeys() 会被缓存
			// 即使当前 SQL 未能成功执行仍然能得到上一次执行成功后缓存在 PrepareStatement 中的 GeneratedKeys
			// 导致项目程序存在 BUG
			// DRUID_DATA_SOURCE.setMaxPoolPreparedStatementPerConnectionSize(20);

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

	/**
	 * 获取数据库连接
	 * 
	 * 
	 * 开发时间：2016-6-16 下午8:41:26
	 * 
	 * @author：崔旧涛
	 * @return
	 */
	public static Connection getConnection() {
		
		LOG.trace("Get DataBase Connection");
		
		try {

			// 返回函数值
			return DRUID_DATA_SOURCE.getConnection();

		} catch (SQLException e) {
			LOG.error("Get DataBase Connection Error Stack", e);
			e.printStackTrace();
		}

		// 返回函数值
		return null;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * 
	 * 开发时间：2016-6-16 下午8:43:19
	 * 
	 * @author：崔旧涛
	 */
	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error("close ResultSet SQLException",e);
				e.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				LOG.error("close PreparedStatement SQLException",e);
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error("close Connection SQLException",e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭数据库连接
	 * 
	 * 
	 * 开发时间：2016-6-16 下午8:43:19
	 * 
	 * @author：崔旧涛
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error("close ResultSet SQLException",e);
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				LOG.error("close Statement SQLException",e);
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error("close Connection SQLException",e);
				e.printStackTrace();
			}
		}
	}

	public static void truncateTable(String sql) {

		Connection conn = getConnection();

		Statement stmt = null;

		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(null, stmt, conn);
		}
	}

	/**
	 * 转换 ResultSet 为 LohHouseType 的方法
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static LohHouseType convertResultSetToLohHouseType(ResultSet rs) throws SQLException {

		LohHouseType lohHouseType = new LohHouseType();

		lohHouseType.setLohHouseTypeId(rs.getInt("loh_house_type_id"));
		lohHouseType.setGmtCreate(rs.getDate("gmt_create"));
		lohHouseType.setGmtModified(rs.getDate("gmt_modified"));
		lohHouseType.setHouseType(rs.getString("house_type"));

		// 返回函数值
		return lohHouseType;
	}

	/**
	 * 转换 ResultSet 为 LohHouseInfo 的方法
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static LohHouseInfo convertResultSetToLohHouseInfo(ResultSet rs) throws SQLException {

		LohHouseInfo lohHouseInfo = new LohHouseInfo();

		lohHouseInfo.setLohHouseInfoId(rs.getInt("loh_house_info_id"));
		lohHouseInfo.setGmtCreate(rs.getDate("gmt_create"));
		lohHouseInfo.setGmtModified(rs.getDate("gmt_modified"));
		lohHouseInfo.setHouseTitle(rs.getString("house_title"));
		lohHouseInfo.setUserInfoId(rs.getInt("user_info_id"));
		lohHouseInfo.setLohHouseTypeId(rs.getInt("loh_house_type_id"));
		lohHouseInfo.setRegionInfoProvinceId(rs.getInt("region_info_province_id"));
		lohHouseInfo.setRegionInfoCityId(rs.getInt("region_info_city_id"));
		lohHouseInfo.setRegionInfoCountyId(rs.getInt("region_info_county_id"));
		lohHouseInfo.setHouseAddress(rs.getString("house_address"));
		lohHouseInfo.setPrice(rs.getBigDecimal("price"));
		lohHouseInfo.setPushDate(rs.getTimestamp("push_date"));
		lohHouseInfo.setContacts(rs.getString("contacts"));
		lohHouseInfo.setCellPhone(rs.getString("cell_phone"));
		lohHouseInfo.setQrcodeLink(rs.getString("qrcode_link"));

		return lohHouseInfo;
	}

	/**
	 * 转换 ResultSet 为 LoginInfo 的方法
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static LoginInfo convertResultSetToLoginInfo(ResultSet rs) throws SQLException {

		LoginInfo loginInfo = new LoginInfo();

		loginInfo.setLoginInfoId(rs.getInt("login_info_id"));
		loginInfo.setGmtCreate(rs.getTimestamp("gmt_create"));
		loginInfo.setGmtModified(rs.getTimestamp("gmt_modified"));
		loginInfo.setLoginAccount(rs.getString("login_account"));
		loginInfo.setLoginPassword(rs.getString("login_password"));
		loginInfo.setUserInfoId(rs.getInt("user_info_id"));
		loginInfo.setCurrentLoginTime(rs.getTimestamp("current_login_time"));
		loginInfo.setLastLoginTime(rs.getTimestamp("last_login_time"));
		loginInfo.setLoginIp(rs.getString("login_ip"));

		return loginInfo;
	}

	/**
	 * 转换 ResultSet 为 RegionInfo 的方法
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static RegionInfo convertResultSetToRegionInfo(ResultSet rs) throws SQLException {

		RegionInfo regionInfo = new RegionInfo();
		
		regionInfo.setRegionInfoId(rs.getInt("region_info_id"));
		regionInfo.setGmtCreate(rs.getTimestamp("gmt_create"));
		regionInfo.setGmtModified(rs.getTimestamp("gmt_modified"));
		regionInfo.setRegionCode(rs.getString("region_code"));
		regionInfo.setRegionName(rs.getString("region_name"));
		regionInfo.setRegionLevel(rs.getInt("region_level"));
		regionInfo.setParentRegionId(rs.getInt("parent_region_id") == 0 ? null : rs.getInt("parent_region_id"));
		
		return regionInfo;
	}
	
	/**
	 * 转换 ResultSet 为 LohFileInfo 的方法
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static LohFileInfo convertResultSetToLohFileInfo(ResultSet rs) throws SQLException {
		
		LohFileInfo lohFileInfo = new LohFileInfo();
		
		lohFileInfo.setLohFileInfoId(rs.getInt("loh_file_info_id"));
		lohFileInfo.setGmtCreate(rs.getDate("gmt_create"));
		lohFileInfo.setGmtModified(rs.getDate("gmt_modified"));
		lohFileInfo.setLohHouseInfoId(rs.getInt("loh_house_info_id"));
		lohFileInfo.setLohFileTypeId(rs.getInt("loh_file_type_id"));
		lohFileInfo.setFileTitle(rs.getString("file_title"));
		lohFileInfo.setFileLink(rs.getString("file_link"));
		
		return lohFileInfo;
	}
	
	/**
	 * 转换 ResultSet 为 UserInfo 的方法
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static UserInfo convertResultSetToUserInfo(ResultSet rs) throws SQLException {
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setUserInfoId(rs.getInt("user_info_id"));
		userInfo.setGmtCreate(rs.getTimestamp("gmt_create"));
		userInfo.setGmtModified(rs.getTimestamp("gmt_modified"));
		userInfo.setUserName(rs.getString("user_name"));
		userInfo.setBornDate(rs.getDate("born_date"));
		userInfo.setRegionInfoProvinceId(rs.getInt("region_info_province_id"));
		userInfo.setRegionInfoCityId(rs.getInt("region_info_city_id"));
		userInfo.setRegionInfoCountyId(rs.getInt("region_info_county_id"));
		userInfo.setCellPhone(rs.getString("cell_phone"));
		userInfo.setDetailedInformation(rs.getString("detailed_information"));
		
		return userInfo;
	}
}
