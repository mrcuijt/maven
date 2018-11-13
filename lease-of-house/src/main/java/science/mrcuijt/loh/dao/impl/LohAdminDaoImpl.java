/**
 * 
 */
package science.mrcuijt.loh.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.Region;

import science.mrcuijt.loh.dao.LohAdminDao;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class LohAdminDaoImpl implements LohAdminDao {

	/**
	 * 根据房屋类型查询房屋类型记录
	 * 
	 * @param houseType
	 * 
	 * @return
	 */
	@Override
	public LohHouseType findLohHouseTypeByHouseType(String houseType) {

		LohHouseType lohHouseType = null;

		StringBuffer strbfindLohHouseType = new StringBuffer();
		strbfindLohHouseType.append(" SELECT ");
		strbfindLohHouseType.append(" loh_house_type_id , ");
		strbfindLohHouseType.append(" gmt_create , ");
		strbfindLohHouseType.append(" gmt_modified , ");
		strbfindLohHouseType.append(" house_type ");
		strbfindLohHouseType.append(" FROM loh_house_type ");
		strbfindLohHouseType.append(" WHERE house_type = ? ");

		String sql = strbfindLohHouseType.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setString(1, houseType);

			rs = ps.executeQuery();

			while (rs.next()) {

				lohHouseType = JDBCUtil.convertResultSetToLohHouseType(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		return lohHouseType;
	}

	/**
	 * 添加房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseType
	 * @return
	 */
	@Override
	public boolean addLohHouseType(LohHouseType lohHouseType) {

		boolean addLohHouseTypeResult = false;

		StringBuffer strbaddLohHouseType = new StringBuffer();

		strbaddLohHouseType.append(" INSERT INTO loh_house_type ");
		strbaddLohHouseType.append(" ( ");
		strbaddLohHouseType.append(" gmt_create , ");
		strbaddLohHouseType.append(" gmt_modified , ");
		strbaddLohHouseType.append(" house_type  ");
		strbaddLohHouseType.append(" ) ");
		strbaddLohHouseType.append(" VALUES ( ? , ? , ? ) ");

		String sql = strbaddLohHouseType.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// 设置自动提交事务为 false
			conn.setAutoCommit(false);

			// Statement.RETURN_GENERATED_KEYS 返回添加数据记录自动增长的值
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1, new Timestamp(lohHouseType.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(lohHouseType.getGmtModified().getTime()));
			ps.setString(3, lohHouseType.getHouseType());

			int addCount = ps.executeUpdate();

			if (addCount > 0) {

				// 查询最后一条数据库生成的 id
				rs = ps.getGeneratedKeys();

				while (rs.next()) {
					lohHouseType.setLohHouseTypeId(rs.getInt(1));
				}

				conn.commit();

				addLohHouseTypeResult = true;

			} else {

				conn.rollback();
			}

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

		// 返回函数值
		return addLohHouseTypeResult;
	}

	/**
	 * 查询所有的房屋类型
	 * 
	 * @return
	 */
	@Override
	public List<LohHouseType> findLohHouseTypeList() {

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
	 * 根据房屋类型Id查询房屋类型
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

		return lohHouseType;
	}

	/**
	 * 根据房屋类型Id查询房屋信息
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public List<LohHouseInfo> findLohHouseInfoByLohHouseTypeId(Integer lohHouseTypeId) {

		List<LohHouseInfo> lohHouseInfoList = new ArrayList<LohHouseInfo>();

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
		strbFindLohHouseInfoByLohHouseTypeId.append(" WHERE loh_house_type_id = ? ");

		String sql = strbFindLohHouseInfoByLohHouseTypeId.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, lohHouseTypeId);

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

		return lohHouseInfoList;
	}

	/**
	 * 添加地区信息的方法
	 * 
	 * @param regionInfo
	 * @return
	 */
	@Override
	public boolean addRegionInfo(RegionInfo regionInfo) {

		boolean addRegionInfoResult = false;

		StringBuffer strbAddRegionInfo = new StringBuffer();

		strbAddRegionInfo.append(" INSERT INTO region_info ");
		strbAddRegionInfo.append(" ( ");
		strbAddRegionInfo.append(" gmt_create , ");
		strbAddRegionInfo.append(" gmt_modified , ");
		strbAddRegionInfo.append(" region_code , ");
		strbAddRegionInfo.append(" region_name , ");
		strbAddRegionInfo.append(" region_level , ");
		strbAddRegionInfo.append(" parent_region_id ");
		strbAddRegionInfo.append(" ) ");
		strbAddRegionInfo.append(" VALUES ( ?, ?, ?, ?, ?,  ? )");

		String sql = strbAddRegionInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// 设置自动提交事务为 false
			conn.setAutoCommit(false);

			// 设置返回自动增长的 id
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1, new Timestamp(regionInfo.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(regionInfo.getGmtModified().getTime()));
			ps.setString(3, regionInfo.getRegionCode());
			ps.setString(4, regionInfo.getRegionName());
			ps.setInt(5, regionInfo.getRegionLevel());
			if (regionInfo.getParentRegionId() == null) {
				ps.setNull(6, Types.INTEGER);
			} else {
				ps.setInt(6, regionInfo.getParentRegionId());
			}

			int count = ps.executeUpdate();

			if (count <= 0) {

				conn.rollback();

				return addRegionInfoResult;
			}

			rs = ps.getGeneratedKeys();

			while (rs.next()) {
				regionInfo.setParentRegionId(rs.getInt(1));
			}

			conn.commit();

			addRegionInfoResult = true;

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

		return addRegionInfoResult;
	}

	/**
	 * 根据地区信息表主键查询地区信息表记录
	 * 
	 * @param regionInfoId
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
	 * 根据地区信息表主键更新地区信息表记录
	 * 
	 * @param regionInfo
	 * @return
	 */
	@Override
	public boolean updateRegionInfoByPrimaryKey(RegionInfo regionInfo) {

		boolean updateRegionInfoResult = false;

		StringBuffer strbAddRegionInfo = new StringBuffer();

		strbAddRegionInfo.append(" UPDATE region_info");
		strbAddRegionInfo.append(" SET ");
		strbAddRegionInfo.append(" gmt_create = ?, ");
		strbAddRegionInfo.append(" gmt_modified = ? , ");
		strbAddRegionInfo.append(" region_code = ? , ");
		strbAddRegionInfo.append(" region_name = ? , ");
		strbAddRegionInfo.append(" region_level = ? , ");
		strbAddRegionInfo.append(" parent_region_id = ? ");
		strbAddRegionInfo.append(" WHERE region_info_id = ? ");

		String sql = strbAddRegionInfo.toString();

		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// 设置自动提交事务为 false
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			ps.setTimestamp(1, new Timestamp(regionInfo.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(regionInfo.getGmtModified().getTime()));
			ps.setString(3, regionInfo.getRegionCode());
			ps.setString(4, regionInfo.getRegionName());
			ps.setInt(5, regionInfo.getRegionLevel());
			if (regionInfo.getParentRegionId() == null) {
				ps.setNull(6, Types.INTEGER);
			} else {
				ps.setInt(6, regionInfo.getParentRegionId());
			}
			ps.setInt(7, regionInfo.getRegionInfoId());

			int count = ps.executeUpdate();

			if (count <= 0) {

				conn.rollback();

				return updateRegionInfoResult;
			}

			conn.commit();

			updateRegionInfoResult = true;

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

		return updateRegionInfoResult;
	}

	/**
	 * 根据区域等级查询所有区域信息
	 * 
	 * @param level
	 * @return
	 */
	@Override
	public List<RegionInfo> findRegionInfoByLevel(Integer level) {

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

			ps.setInt(1, level);

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

}
