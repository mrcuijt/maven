/**
 * 
 */
package science.mrcuijt.loh.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

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

	private static final Logger LOG = Logger.getLogger(JDBCUtil.class);

	private static final DruidDataSource DRUID_DATA_SOURCE = new DruidDataSource();

	private static final String DRVIER = "com.mysql.jdbc.Driver";

	// mysql 数据库连接url jdbc\:mysql\://localhost\:3306/test1
	// jdbc:oracle:thin:@localhost:1521:orcl
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
			DRUID_DATA_SOURCE.setFilters("stat,log4j");
		} catch (Exception e) {
			LOG.info("加载 MySQL JDBC 驱动出现异常", e);
			e.printStackTrace();
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
		LOG.info("获取数据库 Connection 连接");
		try {

			// 返回函数值
			return DRUID_DATA_SOURCE.getConnection();

		} catch (SQLException e) {
			LOG.info("获取数据库 Connection 连接异常", e);
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
				e.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
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
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
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
		lohHouseInfo.setPushDate(rs.getDate("push_date"));
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
