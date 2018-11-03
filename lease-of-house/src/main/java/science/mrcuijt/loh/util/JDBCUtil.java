/**
 * 
 */
package science.mrcuijt.loh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;

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
			//Class.forName(DRVIER);
			DRUID_DATA_SOURCE.setUsername(USER);
			DRUID_DATA_SOURCE.setPassword(PASSWORD);
			DRUID_DATA_SOURCE.setUrl(URL);
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
		}finally {
			JDBCUtil.closeAll(null, stmt, conn);
		}
	}
}
