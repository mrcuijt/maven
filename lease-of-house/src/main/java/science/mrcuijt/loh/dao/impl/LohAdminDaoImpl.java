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
import java.util.ArrayList;
import java.util.List;

import science.mrcuijt.loh.dao.LohAdminDao;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
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
		strbFindLohHouseInfoByLohHouseTypeId.append(" loh_file_info_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" loh_house_type_id , ");
		strbFindLohHouseInfoByLohHouseTypeId.append(" region_info_id , ");
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
			
			while(rs.next()) {
				
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

	
	
	

}
