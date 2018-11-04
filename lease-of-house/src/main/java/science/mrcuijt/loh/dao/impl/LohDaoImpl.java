/**
 * 
 */
package science.mrcuijt.loh.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class LohDaoImpl implements LohDao {

	private static final Logger LOG = Logger.getLogger(LohDaoImpl.class);

	/**
	 * 保存用户信息和登录信息的方法
	 * 
	 * @param userInfo
	 * @param loginInfo
	 * @return
	 */
	@Override
	public boolean saveUserInfoAndLoginInfo(UserInfo userInfo, LoginInfo loginInfo) {

		boolean result = false;

		// 添加用户信息表的 SQL
		StringBuffer strbAddUserInfoSQL = new StringBuffer();
		strbAddUserInfoSQL.append(" INSERT INTO user_info ( ");
		strbAddUserInfoSQL.append(" gmt_create, ");
		strbAddUserInfoSQL.append(" gmt_modified, ");
		strbAddUserInfoSQL.append(" user_name, ");
		strbAddUserInfoSQL.append(" born_date, ");
		strbAddUserInfoSQL.append(" region_info_id, ");
		strbAddUserInfoSQL.append(" cell_phone, ");
		strbAddUserInfoSQL.append(" detailed_information ");
		strbAddUserInfoSQL.append(" ) ");
		strbAddUserInfoSQL.append(" VALUES ( ? , ? , ? , ? , ? , ? , ? ) ");

		String addUserInfoSQL = strbAddUserInfoSQL.toString();

		// 添加登录信息表的 SQL
		StringBuffer strbAddLoginInfoSQL = new StringBuffer();
		strbAddLoginInfoSQL.append(" INSERT INTO login_info ( ");
		strbAddLoginInfoSQL.append(" gmt_create ,");
		strbAddLoginInfoSQL.append(" gmt_modified ,");
		strbAddLoginInfoSQL.append(" login_account , ");
		strbAddLoginInfoSQL.append(" login_password ,");
		strbAddLoginInfoSQL.append(" user_info_id , ");
		strbAddLoginInfoSQL.append(" current_login_time , ");
		strbAddLoginInfoSQL.append(" last_login_time , ");
		strbAddLoginInfoSQL.append(" login_ip ");
		strbAddLoginInfoSQL.append(" ) ");
		strbAddLoginInfoSQL.append(" VALUES ( ? , ? , ? , ? , ? , ? , ? , ? ) ");

		String addLoginInfoSQL = strbAddLoginInfoSQL.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn.setAutoCommit(false); // 设置自动提交 commit 为 false 。相当于 beginTransaction

			// MySQL 驱动包版本升级到 5.1.17 之后获取自动增长的主键时需要在创建 PrepareStatement 对象是，添加
			// PreparedStatement.RETURN_GENERATED_KEYS 参数
			ps = conn.prepareStatement(addUserInfoSQL, PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1,
					userInfo.getGmtCreate() != null ? new Timestamp(userInfo.getGmtCreate().getTime()) : null);
			ps.setTimestamp(2,
					userInfo.getGmtModified() != null ? new Timestamp(userInfo.getGmtModified().getTime()) : null);
			ps.setString(3, userInfo.getUserName());
			ps.setDate(4, userInfo.getBornDate() != null ? new java.sql.Date(userInfo.getBornDate().getTime()) : null);
			if (userInfo.getRegionInfoId() == null || userInfo.getRegionInfoId() <= 0) {
				ps.setNull(5, Types.NULL);
			} else {
				ps.setInt(5, userInfo.getRegionInfoId());
			}
			ps.setString(6, userInfo.getCellPhone());
			ps.setString(7, userInfo.getDetailedInformation());

			Integer addUserInfoResult = ps.executeUpdate();

			if (addUserInfoResult > 0) {

				// 查询最后一条数据库生成的 id
				rs = ps.getGeneratedKeys();

				while (rs.next()) {
					userInfo.setUserInfoId(rs.getInt(1));
				}

				if (userInfo.getUserInfoId() != null && userInfo.getUserInfoId() > 0) {

					// 设置用户 ID
					loginInfo.setUserInfoId(userInfo.getUserInfoId());

					ps = conn.prepareStatement(addLoginInfoSQL, PreparedStatement.RETURN_GENERATED_KEYS);

					ps.setTimestamp(1,
							loginInfo.getGmtCreate() != null ? new Timestamp(loginInfo.getGmtCreate().getTime())
									: null);
					ps.setTimestamp(2,
							loginInfo.getGmtModified() != null ? new Timestamp(loginInfo.getGmtModified().getTime())
									: null);
					ps.setString(3, loginInfo.getLoginAccount());
					ps.setString(4, loginInfo.getLoginPassword());
					ps.setInt(5, loginInfo.getUserInfoId());
					ps.setTimestamp(6,
							loginInfo.getCurrentLoginTime() != null
									? new Timestamp(loginInfo.getCurrentLoginTime().getTime())
									: null);
					ps.setTimestamp(7,
							loginInfo.getLastLoginTime() != null ? new Timestamp(loginInfo.getLastLoginTime().getTime())
									: null);
					ps.setString(8, loginInfo.getLoginIp());

					Integer addLoginInfoResult = ps.executeUpdate();

					if (addLoginInfoResult > 0) {

						// 查询最后一条数据库生成的 id
						rs = ps.getGeneratedKeys();

						while (rs.next()) {
							loginInfo.setLoginInfoId(rs.getInt(1));
						}

						if (loginInfo.getLoginInfoId() == null && loginInfo.getLoginInfoId() <= 0) {
							// 查询最后一条添加记录出错，回滚事务
							LOG.debug("查询最后一条添加记录出错，回滚事务");
							conn.rollback();
							return false;
						}

						// 提交事务，保存到数据
						conn.commit();

						// 设置操作结果为 true
						result = true;

					} else {
						// 添加用户登录信息记录出错，回滚事务
						LOG.debug("添加用户登录信息记录出错，回滚事务");
						conn.rollback();
						return false;
					}

				} else {
					// 查询最后一条添加记录出错，回滚事务
					LOG.debug("查询最后一条添加记录出错，回滚事务");
					conn.rollback();
					return false;
				}

			} else {
				// 添加用户信息记录出错，回滚事务
				LOG.debug("添加用户信息记录出错，回滚事务");
				conn.rollback();
				return false;
			}

		} catch (SQLException e) {

			LOG.error("science.mrcuijt.loh.util.JDBCUtil.saveUserInfoAndLoginInfo(UserInfo, LoginInfo) error", e);

			try {

				conn.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		return result;
	}

	/**
	 * 找寻登录信息的方法
	 * 
	 * @param loginName
	 * @param loginPassword
	 * @return
	 */
	@Override
	public LoginInfo findLoginInfo(String loginName, String loginPassword) {

		LoginInfo loginInfo = null;

		StringBuffer strbFindLoginInfo = new StringBuffer();
		strbFindLoginInfo.append(" SELECT ");
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
		strbFindLoginInfo.append(" WHERE login_account = ? ");
		strbFindLoginInfo.append(" AND login_password = ? ");

		String findLoginInfoSQL = strbFindLoginInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = conn.prepareStatement(findLoginInfoSQL);

			ps.setString(1, loginName);
			ps.setString(2, loginPassword);

			rs = ps.executeQuery();

			while (rs.next()) {

				loginInfo = JDBCUtil.convertResultSetToLoginInfo(rs);
				;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		return loginInfo;
	}

	/**
	 * 
	 * 找寻用户信息的方法
	 * 
	 * @param userInfoId
	 * @return
	 */
	@Override
	public UserInfo findUserInfoByPrimaryKey(Integer userInfoId) {

		UserInfo userInfo = null;

		StringBuffer strbFindUserInfo = new StringBuffer();
		strbFindUserInfo.append(" SELECT ");
		strbFindUserInfo.append(" user_info_id , ");
		strbFindUserInfo.append(" gmt_create, ");
		strbFindUserInfo.append(" gmt_modified, ");
		strbFindUserInfo.append(" user_name, ");
		strbFindUserInfo.append(" born_date, ");
		strbFindUserInfo.append(" region_info_id, ");
		strbFindUserInfo.append(" cell_phone, ");
		strbFindUserInfo.append(" detailed_information ");
		strbFindUserInfo.append(" FROM user_info ");
		strbFindUserInfo.append(" WHERE user_info_id = ? ");

		String findUserInfoSQL = strbFindUserInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = conn.prepareStatement(findUserInfoSQL);

			ps.setInt(1, userInfoId);

			rs = ps.executeQuery();

			while (rs.next()) {

				userInfo = new UserInfo();

				userInfo.setUserInfoId(rs.getInt("user_info_id"));
				userInfo.setGmtCreate(rs.getTimestamp("gmt_create"));
				userInfo.setGmtModified(rs.getTimestamp("gmt_modified"));
				userInfo.setUserName(rs.getString("user_name"));
				userInfo.setBornDate(rs.getDate("born_date"));
				userInfo.setRegionInfoId(rs.getInt("region_info_id"));
				userInfo.setCellPhone(rs.getString("cell_phone"));
				userInfo.setDetailedInformation(rs.getString("detailed_information"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		return userInfo;
	}

	/**
	 * 根据 loginName 找寻用户信息的方法
	 * 
	 * @param loginName
	 * @return
	 */
	@Override
	public LoginInfo findLoginInfoByLoginName(String loginName) {

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
		strbFindLoginInfo.append(" WHERE login_account = ? ");

		String findLoginInfoSQL = strbFindLoginInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = conn.prepareStatement(findLoginInfoSQL);

			ps.setString(1, loginName);

			rs = ps.executeQuery();

			while (rs.next()) {

				loginInfo = JDBCUtil.convertResultSetToLoginInfo(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		return loginInfo;
	}

	/**
	 * 更新 LoginInfo 的方法
	 * 
	 * @param loginInfo
	 * @return
	 */
	@Override
	public boolean updateLoginInfo(LoginInfo loginInfo) {

		boolean updateResult = false;

		StringBuffer strbUpdateLoginInfo = new StringBuffer();

		strbUpdateLoginInfo.append(" UPDATE ");
		strbUpdateLoginInfo.append(" login_info ");
		strbUpdateLoginInfo.append(" SET ");
		strbUpdateLoginInfo.append(" gmt_modified = ? , ");
		strbUpdateLoginInfo.append(" login_account = ? , ");
		strbUpdateLoginInfo.append(" login_password = ? , ");
		strbUpdateLoginInfo.append(" user_info_id = ? , ");
		strbUpdateLoginInfo.append(" current_login_time = ? , ");
		strbUpdateLoginInfo.append(" last_login_time = ? , ");
		strbUpdateLoginInfo.append(" login_ip = ? ");
		strbUpdateLoginInfo.append(" WHERE ");
		strbUpdateLoginInfo.append(" login_info_id = ? ");

		String strbUpdateLoginInfoSQL = strbUpdateLoginInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;

		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(strbUpdateLoginInfoSQL);

			ps.setTimestamp(1, new Timestamp(loginInfo.getGmtModified().getTime()));
			ps.setString(2, loginInfo.getLoginAccount());
			ps.setString(3, loginInfo.getLoginPassword());
			ps.setInt(4, loginInfo.getUserInfoId());
			ps.setTimestamp(5, new Timestamp(loginInfo.getCurrentLoginTime().getTime()));
			if (loginInfo.getLastLoginTime() == null) {
				ps.setNull(6, Types.TIMESTAMP);
			} else {
				ps.setTimestamp(6, loginInfo.getLastLoginTime() == null ? null
						: new Timestamp(loginInfo.getLastLoginTime().getTime()));
			}
			ps.setString(7, loginInfo.getLoginIp());
			ps.setInt(8, loginInfo.getLoginInfoId());

			int updageResultCount = ps.executeUpdate();

			if (updageResultCount > 0) {
				conn.commit();
				updateResult = true;
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(null, ps, conn);
		}

		// 返回函数值
		return updateResult;
	}

	/**
	 * 添加 LohHouseInfo 的方法
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	@Override
	public boolean addLohHouseInfo(LohHouseInfo lohHouseInfo) {

		boolean addLohHouseInfoResult = false;

		StringBuffer strbAddLohHouseInfo = new StringBuffer();

		strbAddLohHouseInfo.append(" INSERT INTO loh_house_info ");
		strbAddLohHouseInfo.append(" ( ");
		strbAddLohHouseInfo.append(" gmt_create , ");
		strbAddLohHouseInfo.append(" gmt_modified , ");
		strbAddLohHouseInfo.append(" house_title , ");
		strbAddLohHouseInfo.append(" user_info_id , ");
		strbAddLohHouseInfo.append(" loh_house_type_id , ");
		strbAddLohHouseInfo.append(" region_info_id , ");
		strbAddLohHouseInfo.append(" house_address , ");
		strbAddLohHouseInfo.append(" price , ");
		strbAddLohHouseInfo.append(" push_date , ");
		strbAddLohHouseInfo.append(" contacts , ");
		strbAddLohHouseInfo.append(" cell_phone , ");
		strbAddLohHouseInfo.append(" qrcode_link ");
		strbAddLohHouseInfo.append(" ) ");
		strbAddLohHouseInfo.append(" VALUES ( ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ? )");

		String sql = strbAddLohHouseInfo.toString();

		Connection conn = JDBCUtil.getConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1, new Timestamp(lohHouseInfo.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(lohHouseInfo.getGmtModified().getTime()));
			ps.setString(3, lohHouseInfo.getHouseTitle());
			ps.setInt(4, lohHouseInfo.getUserInfoId());
			ps.setInt(5, lohHouseInfo.getLohHouseTypeId());
			if (lohHouseInfo.getRegionInfoId() == null) {
				ps.setNull(6, Types.INTEGER);
			} else {
				ps.setInt(6, lohHouseInfo.getRegionInfoId());
			}
			ps.setString(7, lohHouseInfo.getHouseAddress());
			ps.setBigDecimal(8, lohHouseInfo.getPrice());
			ps.setDate(9, new Date(lohHouseInfo.getPushDate().getTime()));
			ps.setString(10, lohHouseInfo.getContacts());
			ps.setString(11, lohHouseInfo.getCellPhone());
			ps.setString(12, lohHouseInfo.getQrcodeLink());

			int addLohHouseInfoCount = ps.executeUpdate();

			if (addLohHouseInfoCount > 0) {

				// 获取自动增长列
				rs = ps.getGeneratedKeys();

				while (rs.next()) {

					// 设置 LohHouseInfoId
					lohHouseInfo.setLohHouseInfoId(rs.getInt(1));
				}

			} else {
				conn.rollback();
			}

			// 提交事务
			conn.commit();

			addLohHouseInfoResult = true;

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

		return addLohHouseInfoResult;
	}

	/**
	 * 用户发布法务信息（LohHouseInfo）的分页查询方法
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	@Override
	public Map<String, Object> queryHouseInfoPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam) {

		Map<String, Object> queryPagination = new HashMap<String, Object>();

		List<LohHouseInfo> lohHouseInfoList = new ArrayList<LohHouseInfo>();

		Integer totalRecord = 0;
		Integer totalPage = 0;

		// 拼接公共查询语句
		StringBuffer strbComm = new StringBuffer();
		strbComm.append(" FROM loh_house_info ");
		strbComm.append(" WHERE user_info_id = ? ");
		// 动态拼接查询条件
		strbComm.append("  ");

		// 查询总记录数
		StringBuffer strbCount = new StringBuffer();
		strbCount.append(" SELECT count(distinct loh_house_info_id) ");
		strbCount.append(strbComm.toString());

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = conn.prepareStatement(strbCount.toString());

			ps.setInt(1, (int) queryParam.get("userInfoId"));

			// 动态添加查询条件参数

			rs = ps.executeQuery();

			while (rs.next()) {

				totalRecord = rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, null);
		}

		if (totalRecord <= 0) {
			totalRecord = 1;
		}

		totalPage = totalRecord / pageSize;
		if (totalRecord % pageSize > 0) {
			totalPage++;
		}

		if (totalPage < pageIndex) {
			pageIndex = totalPage;
		}

		// 分页查询
		StringBuffer strbQueryPagination = new StringBuffer();
		strbQueryPagination.append(" SELECT ");
		strbQueryPagination.append(" loh_house_info_id , ");
		strbQueryPagination.append(" gmt_create , ");
		strbQueryPagination.append(" gmt_modified , ");
		strbQueryPagination.append(" house_title , ");
		strbQueryPagination.append(" user_info_id , ");
		strbQueryPagination.append(" loh_house_type_id , ");
		strbQueryPagination.append(" region_info_id , ");
		strbQueryPagination.append(" house_address , ");
		strbQueryPagination.append(" price , ");
		strbQueryPagination.append(" push_date , ");
		strbQueryPagination.append(" contacts , ");
		strbQueryPagination.append(" cell_phone , ");
		strbQueryPagination.append(" qrcode_link ");
		strbQueryPagination.append(strbComm.toString());
		strbQueryPagination.append(" ORDER BY push_date desc ");
		strbQueryPagination.append(" LIMIT ");
		strbQueryPagination.append((pageIndex - 1) * pageSize);
		strbQueryPagination.append(" , ");
		strbQueryPagination.append(pageSize);

		try {

			ps = conn.prepareStatement(strbQueryPagination.toString());

			ps.setInt(1, (int) queryParam.get("userInfoId"));
			// 动态添加查询条件参数

			rs = ps.executeQuery();

			while (rs.next()) {

				LohHouseInfo lohHouseInfo = JDBCUtil.convertResultSetToLohHouseInfo(rs);
				lohHouseInfoList.add(lohHouseInfo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		queryPagination.put("totalPage", totalPage);
		queryPagination.put("totalRecord", totalRecord);
		queryPagination.put("pageIndex", pageIndex);
		queryPagination.put("pageSize", pageSize);
		queryPagination.put("pagination", lohHouseInfoList);

		return queryPagination;
	}

}
