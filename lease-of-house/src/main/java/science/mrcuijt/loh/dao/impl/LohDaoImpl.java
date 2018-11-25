/**
 * 
 */
package science.mrcuijt.loh.dao.impl;

import java.math.BigDecimal;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class LohDaoImpl implements LohDao {

	private static final Logger LOG = LoggerFactory.getLogger(LohDaoImpl.class);

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
		strbAddUserInfoSQL.append(" region_info_province_id , ");
		strbAddUserInfoSQL.append(" region_info_city_id , ");
		strbAddUserInfoSQL.append(" region_info_county_id , ");
		strbAddUserInfoSQL.append(" cell_phone, ");
		strbAddUserInfoSQL.append(" detailed_information ");
		strbAddUserInfoSQL.append(" ) ");
		strbAddUserInfoSQL.append(" VALUES ( ?, ?, ?, ?, ?,  ?, ?, ?, ? ) ");

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

			ps.setTimestamp(1, userInfo.getGmtCreate() != null ? new Timestamp(userInfo.getGmtCreate().getTime()) : null);
			ps.setTimestamp(2, userInfo.getGmtModified() != null ? new Timestamp(userInfo.getGmtModified().getTime()) : null);
			ps.setString(3, userInfo.getUserName());
			ps.setDate(4, userInfo.getBornDate() != null ? new java.sql.Date(userInfo.getBornDate().getTime()) : null);
			if (userInfo.getRegionInfoProvinceId() == null) {
				ps.setNull(5, Types.INTEGER);
			} else {
				ps.setInt(5, userInfo.getRegionInfoProvinceId());
			}
			if (userInfo.getRegionInfoCityId() == null) {
				ps.setNull(6, Types.INTEGER);
			} else {
				ps.setInt(6, userInfo.getRegionInfoCityId());
			}
			if (userInfo.getRegionInfoCountyId() == null) {
				ps.setNull(7, Types.INTEGER);
			} else {
				ps.setInt(7, userInfo.getRegionInfoCountyId());
			}
			ps.setString(8, userInfo.getCellPhone());
			ps.setString(9, userInfo.getDetailedInformation());

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

					ps.setTimestamp(1, loginInfo.getGmtCreate() != null ? new Timestamp(loginInfo.getGmtCreate().getTime()) : null);
					ps.setTimestamp(2, loginInfo.getGmtModified() != null ? new Timestamp(loginInfo.getGmtModified().getTime()) : null);
					ps.setString(3, loginInfo.getLoginAccount());
					ps.setString(4, loginInfo.getLoginPassword());
					ps.setInt(5, loginInfo.getUserInfoId());
					ps.setTimestamp(6, loginInfo.getCurrentLoginTime() != null ? new Timestamp(loginInfo.getCurrentLoginTime().getTime()) : null);
					ps.setTimestamp(7, loginInfo.getLastLoginTime() != null ? new Timestamp(loginInfo.getLastLoginTime().getTime()) : null);
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
		strbFindUserInfo.append(" region_info_province_id , ");
		strbFindUserInfo.append(" region_info_city_id , ");
		strbFindUserInfo.append(" region_info_county_id , ");
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

				userInfo = JDBCUtil.convertResultSetToUserInfo(rs);

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
				ps.setTimestamp(6, loginInfo.getLastLoginTime() == null ? null : new Timestamp(loginInfo.getLastLoginTime().getTime()));
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
		strbAddLohHouseInfo.append(" region_info_province_id , ");
		strbAddLohHouseInfo.append(" region_info_city_id , ");
		strbAddLohHouseInfo.append(" region_info_county_id , ");
		strbAddLohHouseInfo.append(" house_address , ");
		strbAddLohHouseInfo.append(" price , ");
		strbAddLohHouseInfo.append(" push_date , ");
		strbAddLohHouseInfo.append(" contacts , ");
		strbAddLohHouseInfo.append(" cell_phone , ");
		strbAddLohHouseInfo.append(" qrcode_link ");
		strbAddLohHouseInfo.append(" ) ");
		strbAddLohHouseInfo.append(" VALUES ( ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ? )");

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
			if (lohHouseInfo.getRegionInfoProvinceId() == null) {
				ps.setNull(6, Types.NULL);
			} else {
				ps.setInt(6, lohHouseInfo.getRegionInfoProvinceId());
			}
			if (lohHouseInfo.getRegionInfoCityId() == null) {
				ps.setNull(7, Types.NULL);
			} else {
				ps.setInt(7, lohHouseInfo.getRegionInfoCityId());
			}
			if (lohHouseInfo.getRegionInfoCountyId() == null) {
				ps.setNull(8, Types.NULL);
			} else {
				ps.setInt(8, lohHouseInfo.getRegionInfoCountyId());
			}
			ps.setString(9, lohHouseInfo.getHouseAddress());
			ps.setBigDecimal(10, lohHouseInfo.getPrice());
			ps.setDate(11, new Date(lohHouseInfo.getPushDate().getTime()));
			ps.setString(12, lohHouseInfo.getContacts());
			ps.setString(13, lohHouseInfo.getCellPhone());
			ps.setString(14, lohHouseInfo.getQrcodeLink());

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
				return addLohHouseInfoResult;
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
	 * 添加房屋信息和房屋文件信息的信息
	 * 
	 * @param lohHouseInfo
	 * @param lohFileInfoList
	 */
	@Override
	public boolean addLohHouseInfo(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList) {

		boolean addLohHouseInfoResult = false;

		StringBuffer strbAddLohHouseInfo = new StringBuffer();

		strbAddLohHouseInfo.append(" INSERT INTO loh_house_info ");
		strbAddLohHouseInfo.append(" ( ");
		strbAddLohHouseInfo.append(" gmt_create , ");
		strbAddLohHouseInfo.append(" gmt_modified , ");
		strbAddLohHouseInfo.append(" house_title , ");
		strbAddLohHouseInfo.append(" user_info_id , ");
		strbAddLohHouseInfo.append(" loh_house_type_id , ");
		strbAddLohHouseInfo.append(" region_info_province_id , ");
		strbAddLohHouseInfo.append(" region_info_city_id , ");
		strbAddLohHouseInfo.append(" region_info_county_id , ");
		strbAddLohHouseInfo.append(" house_address , ");
		strbAddLohHouseInfo.append(" price , ");
		strbAddLohHouseInfo.append(" push_date , ");
		strbAddLohHouseInfo.append(" contacts , ");
		strbAddLohHouseInfo.append(" cell_phone , ");
		strbAddLohHouseInfo.append(" qrcode_link ");
		strbAddLohHouseInfo.append(" ) ");
		strbAddLohHouseInfo.append(" VALUES ( ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ? )");

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

		// 添加房屋信息的 SQL
		String addLohHouseInfoSQL = strbAddLohHouseInfo.toString();

		// 添加房屋文件信息的 SQL
		String addLohFileInfoSQL = strbAddLohFileInfo.toString();

		Connection conn = JDBCUtil.getConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(addLohHouseInfoSQL, Statement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1, new Timestamp(lohHouseInfo.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(lohHouseInfo.getGmtModified().getTime()));
			ps.setString(3, lohHouseInfo.getHouseTitle());
			ps.setInt(4, lohHouseInfo.getUserInfoId());
			ps.setInt(5, lohHouseInfo.getLohHouseTypeId());
			if (lohHouseInfo.getRegionInfoProvinceId() == null) {
				ps.setNull(6, Types.NULL);
			} else {
				ps.setInt(6, lohHouseInfo.getRegionInfoProvinceId());
			}
			if (lohHouseInfo.getRegionInfoCityId() == null) {
				ps.setNull(7, Types.NULL);
			} else {
				ps.setInt(7, lohHouseInfo.getRegionInfoCityId());
			}
			if (lohHouseInfo.getRegionInfoCountyId() == null) {
				ps.setNull(8, Types.NULL);
			} else {
				ps.setInt(8, lohHouseInfo.getRegionInfoCountyId());
			}
			ps.setString(9, lohHouseInfo.getHouseAddress());
			ps.setBigDecimal(10, lohHouseInfo.getPrice());
			ps.setDate(11, new Date(lohHouseInfo.getPushDate().getTime()));
			ps.setString(12, lohHouseInfo.getContacts());
			ps.setString(13, lohHouseInfo.getCellPhone());
			ps.setString(14, lohHouseInfo.getQrcodeLink());

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
				return addLohHouseInfoResult;
			}
			JDBCUtil.closeAll(rs, ps, null);
			if (lohHouseInfo.getLohHouseInfoId() != null && lohHouseInfo.getLohHouseInfoId() > 0) {

				// 添加房屋文件信息记录
				Iterator<LohFileInfo> lohFileInfoIterator = lohFileInfoList.iterator();
				while (lohFileInfoIterator.hasNext()) {
					LohFileInfo lohFileInfo = lohFileInfoIterator.next();
					// 设置房屋文件信息记录所属的房屋信息
					lohFileInfo.setLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());

					ps = conn.prepareStatement(addLohFileInfoSQL, Statement.RETURN_GENERATED_KEYS);
					ps.setTimestamp(1, new Timestamp(lohFileInfo.getGmtCreate().getTime()));
					ps.setTimestamp(2, new Timestamp(lohFileInfo.getGmtModified().getTime()));
					ps.setInt(3, lohFileInfo.getLohHouseInfoId());
					ps.setInt(4, lohFileInfo.getLohFileTypeId());
					ps.setString(5, lohFileInfo.getFileTitle());
					ps.setString(6, lohFileInfo.getFileLink());

					int addLohFileInfoCount = ps.executeUpdate();

					if (addLohFileInfoCount > 0) {

						// 获取自动增长列
						rs = ps.getGeneratedKeys();

						while (rs.next()) {

							// 设置 LohHouseInfoId
							lohFileInfo.setLohFileInfoId(rs.getInt(1));
						}

					} else {
						conn.rollback();
						return addLohHouseInfoResult;
					}
					JDBCUtil.closeAll(rs, ps, null);
				}

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
	 * 根据房屋信息 Id 更新房屋信息
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	@Override
	public boolean updateHouseInfoResult(LohHouseInfo lohHouseInfo) {

		boolean updateLohHouseInfoResult = false;

		StringBuffer strbAddLohHouseInfo = new StringBuffer();

		strbAddLohHouseInfo.append(" UPDATE loh_house_info ");
		strbAddLohHouseInfo.append(" SET ");
		strbAddLohHouseInfo.append(" gmt_create = ? , ");
		strbAddLohHouseInfo.append(" gmt_modified = ? , ");
		strbAddLohHouseInfo.append(" house_title = ? , ");
		strbAddLohHouseInfo.append(" user_info_id = ? , ");
		strbAddLohHouseInfo.append(" loh_house_type_id = ? , ");
		strbAddLohHouseInfo.append(" region_info_province_id = ? , ");
		strbAddLohHouseInfo.append(" region_info_city_id = ? , ");
		strbAddLohHouseInfo.append(" region_info_county_id = ? , ");
		strbAddLohHouseInfo.append(" house_address = ? , ");
		strbAddLohHouseInfo.append(" price = ? , ");
		strbAddLohHouseInfo.append(" push_date = ? , ");
		strbAddLohHouseInfo.append(" contacts = ? , ");
		strbAddLohHouseInfo.append(" cell_phone = ? , ");
		strbAddLohHouseInfo.append(" qrcode_link = ? ");
		strbAddLohHouseInfo.append(" WHERE loh_house_info_id = ? ");

		String sql = strbAddLohHouseInfo.toString();

		Connection conn = JDBCUtil.getConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			ps.setTimestamp(1, new Timestamp(lohHouseInfo.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(lohHouseInfo.getGmtModified().getTime()));
			ps.setString(3, lohHouseInfo.getHouseTitle());
			ps.setInt(4, lohHouseInfo.getUserInfoId());
			ps.setInt(5, lohHouseInfo.getLohHouseTypeId());
			if (lohHouseInfo.getRegionInfoProvinceId() == null) {
				ps.setNull(6, Types.NULL);
			} else {
				ps.setInt(6, lohHouseInfo.getRegionInfoProvinceId());
			}
			if (lohHouseInfo.getRegionInfoCityId() == null) {
				ps.setNull(7, Types.NULL);
			} else {
				ps.setInt(7, lohHouseInfo.getRegionInfoCityId());
			}
			if (lohHouseInfo.getRegionInfoCountyId() == null) {
				ps.setNull(8, Types.NULL);
			} else {
				ps.setInt(8, lohHouseInfo.getRegionInfoCountyId());
			}
			ps.setString(9, lohHouseInfo.getHouseAddress());
			ps.setBigDecimal(10, lohHouseInfo.getPrice());
			ps.setDate(11, new Date(lohHouseInfo.getPushDate().getTime()));
			ps.setString(12, lohHouseInfo.getContacts());
			ps.setString(13, lohHouseInfo.getCellPhone());
			ps.setString(14, lohHouseInfo.getQrcodeLink());
			ps.setInt(15, lohHouseInfo.getLohHouseInfoId());
			
			int updateLohHouseInfoCount = ps.executeUpdate();

			if (updateLohHouseInfoCount <= 0) {
				conn.rollback();
				return updateLohHouseInfoResult;
			}

			// 提交事务
			conn.commit();

			updateLohHouseInfoResult = true;

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

		return updateLohHouseInfoResult;
	}

	/**
	 * 删除房屋信息
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public boolean deleteLohHouseInfoByPrimaryKey(Integer lohHouseInfoId) {

		boolean deleteLohHouseInfoResult = false;
		
		StringBuffer strbDeleteLohHouseInfo = new StringBuffer();
		strbDeleteLohHouseInfo.append(" DELETE FROM loh_house_info ");
		strbDeleteLohHouseInfo.append(" WHERE loh_house_info_id = ? ");
		
		String sql = strbDeleteLohHouseInfo.toString();
		
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		
		try {
			
			// 关闭自动事务提交
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, lohHouseInfoId);
			
			int deleteCount = ps.executeUpdate();
			
			if(deleteCount <= 0) {
				
				// 回滚事务
				conn.rollback();
				
				// 返回函数值
				return deleteLohHouseInfoResult;
			}
			
			// 提交事务
			conn.commit();
			
			deleteLohHouseInfoResult = true;
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			JDBCUtil.closeAll(null, ps, conn);
		}
		
		// 返回函数值
		return deleteLohHouseInfoResult;
	}

	/**
	 * 用户发布房屋信息（LohHouseInfo）的分页查询方法
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

		LinkedList<Object> queryParamValue = new LinkedList<>();

		Integer totalRecord = 0;
		Integer totalPage = 0;

		// 拼接公共查询语句
		StringBuffer strbComm = new StringBuffer();
		strbComm.append(" FROM loh_house_info ");
		strbComm.append(" WHERE user_info_id = ? ");
		
		// 动态拼接查询条件
		strbComm.append("  ");
		
		if (queryParam.get("provinceId") != null) {
			strbComm.append(" and region_info_province_id = ? ");
			queryParamValue.add(queryParam.get("provinceId"));
		}
		
		// 房屋所在市id
		if (queryParam.get("cityId") != null) {
			strbComm.append(" and region_info_city_id = ? ");
			queryParamValue.add(queryParam.get("cityId"));
		}
		
		// 房屋所在县id
		if (queryParam.get("countyId") != null) {
			strbComm.append(" and region_info_county_id = ? ");
			queryParamValue.add(queryParam.get("countyId"));
		}
		
		// 房屋类型 id
		if (queryParam.get("lohHouseTypeId") != null) {
			strbComm.append(" and loh_house_type_id = ? ");
			queryParamValue.add(queryParam.get("lohHouseTypeId"));
		}
		
		// 房屋价格
		if (queryParam.get("lohPrice") != null) {
			strbComm.append(" and price = ? ");
			queryParamValue.add(queryParam.get("lohPrice"));
		}
		
		// 房屋地址
		if (queryParam.get("houseAddress") != null) {
			strbComm.append(" and house_address like ? ");
			queryParamValue.add(queryParam.get("houseAddress") + "%");
		}

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

			int paramStart = 2;

			// 动态添加查询条件参数
			for (Object object : queryParamValue) {

				switch (object.getClass().getName()) {
				case "java.lang.Integer":
					ps.setInt(paramStart, (int) object);
					break;
				case "java.math.BigDecimal":
					ps.setBigDecimal(paramStart, (BigDecimal) object);
					break;
				case "java.lang.String":
					ps.setString(paramStart, (String) object);
					break;
				}

				paramStart++;
			}

			rs = ps.executeQuery();

			while (rs.next()) {

				totalRecord = rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, null);
		}

		if (totalRecord < 0) {
			totalRecord = 0;
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
		strbQueryPagination.append(" region_info_province_id , ");
		strbQueryPagination.append(" region_info_city_id , ");
		strbQueryPagination.append(" region_info_county_id , ");
		strbQueryPagination.append(" house_address , ");
		strbQueryPagination.append(" price , ");
		strbQueryPagination.append(" push_date , ");
		strbQueryPagination.append(" contacts , ");
		strbQueryPagination.append(" cell_phone , ");
		strbQueryPagination.append(" qrcode_link ");
		
		// 拼接公共的查询条件
		strbQueryPagination.append(strbComm.toString());
		// 设置排序
		strbQueryPagination.append(" ORDER BY push_date desc ");
		// 设置分页条件
		strbQueryPagination.append(" LIMIT ");
		strbQueryPagination.append(((pageIndex <= 0) ? 1 : pageIndex - 1) * pageSize);
		strbQueryPagination.append(" , ");
		strbQueryPagination.append(pageSize);

		try {

			ps = conn.prepareStatement(strbQueryPagination.toString());

			ps.setInt(1, (int) queryParam.get("userInfoId"));
			// 动态添加查询条件参数

			int paramStart = 2;

			for (Object object : queryParamValue) {

				switch (object.getClass().getName()) {
				case "java.lang.Integer":
					ps.setInt(paramStart, (int) object);
					break;
				case "java.math.BigDecimal":
					ps.setBigDecimal(paramStart, (BigDecimal) object);
					break;
				case "java.lang.String":
					ps.setString(paramStart, (String) object);
					break;
				}

				paramStart++;
			}

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

	/**
	 * 查询所有房屋类型记录
	 * 
	 * @return
	 */
	@Override
	public List<LohHouseType> findAllLohHouseType() {

		List<LohHouseType> lohHouseTypeList = new ArrayList<LohHouseType>();

		StringBuffer strbFindLohHouseTypeList = new StringBuffer();

		strbFindLohHouseTypeList.append(" SELECT ");
		strbFindLohHouseTypeList.append(" loh_house_type_id , ");
		strbFindLohHouseTypeList.append(" gmt_create , ");
		strbFindLohHouseTypeList.append(" gmt_modified , ");
		strbFindLohHouseTypeList.append(" house_type ");
		strbFindLohHouseTypeList.append(" FROM loh_house_type ");

		String sql = strbFindLohHouseTypeList.toString();

		Connection conn = JDBCUtil.getConnection();
		Statement stms = null;
		ResultSet rs = null;

		try {

			stms = conn.createStatement();
			rs = stms.executeQuery(sql);

			while (rs.next()) {

				LohHouseType lohHouseType = JDBCUtil.convertResultSetToLohHouseType(rs);

				lohHouseTypeList.add(lohHouseType);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, stms, conn);
		}

		return lohHouseTypeList;
	}

	/**
	 * 根据地区级别查询地区信息
	 * 
	 * @param regionLevel
	 * @return
	 */
	@Override
	public List<RegionInfo> findRegionInfoByLevel(Integer regionLevel) {

		List<RegionInfo> regionInfoList = new ArrayList<>();

		StringBuffer strbFindRegionInfo = new StringBuffer();

		strbFindRegionInfo.append(" SELECT  ");
		strbFindRegionInfo.append(" region_info_id , ");
		strbFindRegionInfo.append(" gmt_create , ");
		strbFindRegionInfo.append(" gmt_modified , ");
		strbFindRegionInfo.append(" region_code , ");
		strbFindRegionInfo.append(" region_name , ");
		strbFindRegionInfo.append(" region_level , ");
		strbFindRegionInfo.append(" parent_region_id ");
		strbFindRegionInfo.append(" FROM region_info ");
		strbFindRegionInfo.append(" WHERE region_level = ? ");

		String sql = strbFindRegionInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, regionLevel);

			rs = ps.executeQuery();

			while (rs.next()) {

				regionInfoList.add(JDBCUtil.convertResultSetToRegionInfo(rs));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		// 返回函数值
		return regionInfoList;

	}

	/**
	 * 根据父级地区Id查询对应的子集地区信息
	 * 
	 * @param parentRegionId
	 * @return
	 */
	@Override
	public List<RegionInfo> findRegionInfoByParentRegionId(Integer parentRegionId) {

		List<RegionInfo> regionInfoList = new ArrayList<>();

		StringBuffer strbFindRegionInfo = new StringBuffer();

		strbFindRegionInfo.append(" SELECT  ");
		strbFindRegionInfo.append(" region_info_id , ");
		strbFindRegionInfo.append(" gmt_create , ");
		strbFindRegionInfo.append(" gmt_modified , ");
		strbFindRegionInfo.append(" region_code , ");
		strbFindRegionInfo.append(" region_name , ");
		strbFindRegionInfo.append(" region_level , ");
		strbFindRegionInfo.append(" parent_region_id ");
		strbFindRegionInfo.append(" FROM region_info ");
		strbFindRegionInfo.append(" WHERE parent_region_id = ? ");

		String sql = strbFindRegionInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, parentRegionId);

			rs = ps.executeQuery();

			while (rs.next()) {

				regionInfoList.add(JDBCUtil.convertResultSetToRegionInfo(rs));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		// 返回函数值
		return regionInfoList;

	}
	
	/**
	 * 根据父级地区Id查询对应的子集地区信息
	 * 
	 * @param parentRegionId
	 * @return
	 */
	@Override
	public RegionInfo findRegionInfoByPrimaryKey(Integer regionInfoId) {
		
		RegionInfo regionInfo = null;

		StringBuffer strbFindRegionInfo = new StringBuffer();

		strbFindRegionInfo.append(" SELECT  ");
		strbFindRegionInfo.append(" region_info_id , ");
		strbFindRegionInfo.append(" gmt_create , ");
		strbFindRegionInfo.append(" gmt_modified , ");
		strbFindRegionInfo.append(" region_code , ");
		strbFindRegionInfo.append(" region_name , ");
		strbFindRegionInfo.append(" region_level , ");
		strbFindRegionInfo.append(" parent_region_id ");
		strbFindRegionInfo.append(" FROM region_info ");
		strbFindRegionInfo.append(" WHERE region_info_id = ? ");

		String sql = strbFindRegionInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, regionInfoId);

			rs = ps.executeQuery();

			while (rs.next()) {

				regionInfo = JDBCUtil.convertResultSetToRegionInfo(rs);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		// 返回函数值
		return regionInfo;
	}

	/**
	 * 根据主键查询查询房屋类型信息
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId) {

		LohHouseType lohHouseType = null;

		StringBuffer strbfindLohHouseType = new StringBuffer();
		strbfindLohHouseType.append(" SELECT ");
		strbfindLohHouseType.append(" loh_house_type_id , ");
		strbfindLohHouseType.append(" gmt_create , ");
		strbfindLohHouseType.append(" gmt_modified , ");
		strbfindLohHouseType.append(" house_type ");
		strbfindLohHouseType.append(" FROM loh_house_type ");
		strbfindLohHouseType.append(" WHERE loh_house_type_id = ? ");

		String sql = strbfindLohHouseType.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, lohHouseTypeId);

			rs = ps.executeQuery();

			while (rs.next()) {

				lohHouseType = JDBCUtil.convertResultSetToLohHouseType(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		// 返回函数值
		return lohHouseType;
	}

	/**
	 * 根据房屋信息主键查询房屋信息
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public LohHouseInfo findLohHouseInfoByPrimaryKey(Integer lohHouseInfoId) {

		LohHouseInfo lohHouseInfo = null;

		StringBuffer strbFindLohHouseInfoByLohHouseTypeId = new StringBuffer();

		strbFindLohHouseInfoByLohHouseTypeId.append(" SELECT ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" loh_house_info_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" gmt_create , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" gmt_modified , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" house_title , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" user_info_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" loh_house_type_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" region_info_province_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" region_info_city_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" region_info_county_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" house_address , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" price , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" push_date , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" contacts , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" cell_phone , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" qrcode_link  ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" FROM loh_house_info ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" WHERE loh_house_info_id = ? ");

		String sql = strbFindLohHouseInfoByLohHouseTypeId.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, lohHouseInfoId);

			rs = ps.executeQuery();

			if (rs.next()) {

				lohHouseInfo = JDBCUtil.convertResultSetToLohHouseInfo(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		// 返回函数值
		return lohHouseInfo;
	}

	/**
	 * 根据房屋信息 Id 查询房屋文件信息
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public List<LohFileInfo> findLohFileInfoByLohHouseInfoId(Integer lohHouseInfoId) {

		List<LohFileInfo> lohFileInfoList = new ArrayList<>();

		StringBuffer strbFindLohFileInfo = new StringBuffer();
		strbFindLohFileInfo.append(" SELECT ");
		strbFindLohFileInfo.append(" loh_file_info_id , ");
		strbFindLohFileInfo.append(" gmt_create , ");
		strbFindLohFileInfo.append(" gmt_modified , ");
		strbFindLohFileInfo.append(" loh_house_info_id , ");
		strbFindLohFileInfo.append(" loh_file_type_id , ");
		strbFindLohFileInfo.append(" file_title , ");
		strbFindLohFileInfo.append(" file_link ");
		strbFindLohFileInfo.append(" FROM loh_file_info ");
		strbFindLohFileInfo.append(" WHERE loh_house_info_id = ? ");

		String sql = strbFindLohFileInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, lohHouseInfoId);

			rs = ps.executeQuery();

			while (rs.next()) {

				LohFileInfo lohFileInfo = JDBCUtil.convertResultSetToLohFileInfo(rs);

				lohFileInfoList.add(lohFileInfo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		// 返回函数值
		return lohFileInfoList;
	}

	/**
	 * 删除房屋文件信息列表
	 * 
	 * @param deleteLohFileInfoList
	 * @return
	 */
	@Override
	public boolean deleteFileInfoList(List<LohFileInfo> lohFileInfoList) {

		boolean deleteFileInfoResult = false;

		StringBuffer strbDeleteFileInfo = new StringBuffer();
		strbDeleteFileInfo.append(" DELETE FROM loh_file_info ");
		strbDeleteFileInfo.append(" WHERE loh_file_info_id = ? ");

		String sql = strbDeleteFileInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement(sql);

			for (LohFileInfo lohFileInfo : lohFileInfoList) {
				ps.setInt(1, lohFileInfo.getLohFileInfoId());
				// 添加到批量处理的 SQL 队列中
				ps.addBatch();
			}

			// 执行所有的 SQL 语句
			int[] counts = ps.executeBatch();
			
			for (int count : counts) {
				if (count <= 0) {
					conn.rollback();
					return deleteFileInfoResult;
				}
			}
			
			// 提交事务
			conn.commit();
			deleteFileInfoResult = true;
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

		return deleteFileInfoResult;
	}

	/**
	 * 添加房屋文件信息列表
	 * 
	 * @param lohHouseInfo 
	 * @param lohFileInfoList
	 * @return
	 */

	@Override
	public boolean addFileInfoList(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList) {
		
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
			
			// 添加房屋文件信息记录
			Iterator<LohFileInfo> lohFileInfoIterator = lohFileInfoList.iterator();
			
			while (lohFileInfoIterator.hasNext()) {
				
				LohFileInfo lohFileInfo = lohFileInfoIterator.next();
				
				// 设置房屋文件信息记录所属的房屋信息
				lohFileInfo.setLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());
				
				ps.setTimestamp(1, new Timestamp(lohFileInfo.getGmtCreate().getTime()));
				ps.setTimestamp(2, new Timestamp(lohFileInfo.getGmtModified().getTime()));
				ps.setInt(3, lohFileInfo.getLohHouseInfoId());
				ps.setInt(4, lohFileInfo.getLohFileTypeId());
				ps.setString(5, lohFileInfo.getFileTitle());
				ps.setString(6, lohFileInfo.getFileLink());

				// 添加到批待执行的队列中
				ps.addBatch();
			}
			
			// 批量执行所有的 SQL 语句，获取所有执行结果的影响行数
			int[] addLohFileInfoCount = ps.executeBatch();

			// 判断所有 SQL 是否都执行成功
			for (int count : addLohFileInfoCount) {
				if (count <= 0) {
					conn.rollback();
					return addFileInfoListResult;
				}
			}

			// 获取自动增长列
			rs = ps.getGeneratedKeys();
			int count = 0;
			while (rs.next()) {
				lohFileInfoList.get(count).setLohFileInfoId(rs.getInt(1));
				count++;
			}
			
			// 提交事务
			conn.commit();

			addFileInfoListResult = true;

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

		return addFileInfoListResult;
	}

}
