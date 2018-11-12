/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import science.mrcuijt.loh.dao.impl.LohAdminDaoImpl;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class LohAdminDaoTest {

	LohAdminDao lohAdminDao = new LohAdminDaoImpl();

	@Test
	public void testAddLohHouseType() {

		LohHouseType lohHouseType = new LohHouseType();
		lohHouseType.setGmtCreate(new Date());
		lohHouseType.setGmtModified(new Date());
		lohHouseType.setHouseType("一室一厅");

		boolean addResult = lohAdminDao.addLohHouseType(lohHouseType);

		if (addResult) {
			System.out.println("添加成功！");
			System.out.println(JSON.toJSONString(lohHouseType));
		} else {
			System.out.println("添加失败！");
		}
	}

	@Test
	public void testFindLohHouseTypeByHouseType() {

		String houseType = "一室一厅";

		LohHouseType lohHouseType = lohAdminDao.findLohHouseTypeByHouseType(houseType);

		System.out.println(JSON.toJSONString(lohHouseType));
	}

	@Test
	public void testindLohHouseTypeList() {

		List<LohHouseType> lohHouseType = lohAdminDao.findLohHouseTypeList();

		System.out.println(JSON.toJSONString(lohHouseType));
	}

	@Test
	public void testFindLohHouseTypeByPrimaryKey() {

		LohHouseType lohHouseType = lohAdminDao.findLohHouseTypeByPrimaryKey(0);

		System.out.println(JSON.toJSONString(lohHouseType));
	}

	public void addRegionInfoTest() {

		RegionInfo regionInfo = new RegionInfo();

		regionInfo.setGmtCreate(new Date());

		regionInfo.setGmtModified(new Date());

		regionInfo.setParentRegionId(null);

		regionInfo.setRegionCode(null);

		regionInfo.setRegionLevel(1);

		regionInfo.setRegionName("??");

		lohAdminDao.addRegionInfo(regionInfo);

		System.out.println(JSON.toJSONString(regionInfo));
	}

	public void updateRegionInfoTest() {

		RegionInfo regionInfo = lohAdminDao.findRegionInfoByPrimaryKey(2);

		regionInfo.setRegionCode("001");

		regionInfo.setRegionName("北京");

		lohAdminDao.updateRegionInfoByPrimaryKey(regionInfo);

		System.out.println(JSON.toJSONString(regionInfo));
	}

	public void findRegionInfoTest() {

		RegionInfo regionInfo = lohAdminDao.findRegionInfoByPrimaryKey(2);

		System.out.println(JSON.toJSONString(regionInfo));
	}

	// 1、查询所有级别为 1 的 region 信息
	public List<Map<String, Object>> findAllRegionInfo() {

		List<Map<String, Object>> list = new ArrayList<>();

		DruidDataSource dataSource = new DruidDataSource();

		String DRVIER = "com.mysql.jdbc.Driver";

		String URL = "jdbc:mysql://localhost:3306/region?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

		String USER = "";

		String PASSWORD = "";

		dataSource.setUsername(USER);
		dataSource.setPassword(PASSWORD);
		dataSource.setUrl(URL);

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer strb = new StringBuffer();
		strb.append(" SELECT ");
		strb.append(" gmt_create , ");
		strb.append(" gmt_modified , ");
		strb.append(" code , ");
		strb.append(" alias , ");
		strb.append(" parent_code , ");
		strb.append(" type_code , ");
		strb.append(" children_source_link , ");
		strb.append(" level ");
		strb.append(" FROM region ");
		strb.append(" WHERE level = 1");

		String sql = strb.toString();

		try {

			conn = dataSource.getConnection();

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				strb.append(" gmt_create , ");
				strb.append(" gmt_modified , ");
				strb.append(" code , ");
				strb.append(" alias , ");
				strb.append(" parent_code , ");
				strb.append(" type_code , ");
				strb.append(" children_source_link , ");
				strb.append(" level ");

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("GmtCreate", rs.getTimestamp("gmt_create"));
				map.put("GmtModified", rs.getTimestamp("gmt_modified"));
				map.put("Code", rs.getString("code"));
				map.put("Alias", rs.getString("alias"));
				map.put("ParentCode", rs.getString("parent_code"));
				map.put("Level", rs.getInt("level"));

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(JSON.toJSONString(list));

		return list;
	}

	public List<Map<String, Object>> findRegionBy(String key) {

		List<Map<String, Object>> list = new ArrayList<>();

		DruidDataSource dataSource = new DruidDataSource();

		String DRVIER = "com.mysql.jdbc.Driver";

		String URL = "jdbc:mysql://localhost:3306/region?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

		String USER = "";

		String PASSWORD = "";

		dataSource.setUsername(USER);
		dataSource.setPassword(PASSWORD);
		dataSource.setUrl(URL);

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer strb = new StringBuffer();
		strb.append(" SELECT ");
		strb.append(" gmt_create , ");
		strb.append(" gmt_modified , ");
		strb.append(" code , ");
		strb.append(" alias , ");
		strb.append(" parent_code , ");
		strb.append(" type_code , ");
		strb.append(" children_source_link , ");
		strb.append(" level ");
		strb.append(" FROM region ");
		strb.append(" WHERE parent_code = ?");

		String sql = strb.toString();

		try {

			conn = dataSource.getConnection();

			ps = conn.prepareStatement(sql);

			ps.setString(1, key);

			rs = ps.executeQuery();

			while (rs.next()) {

				strb.append(" gmt_create , ");
				strb.append(" gmt_modified , ");
				strb.append(" code , ");
				strb.append(" alias , ");
				strb.append(" parent_code , ");
				strb.append(" type_code , ");
				strb.append(" children_source_link , ");
				strb.append(" level ");

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("GmtCreate", rs.getTimestamp("gmt_create"));
				map.put("GmtModified", rs.getTimestamp("gmt_modified"));
				map.put("Code", rs.getString("code"));
				map.put("Alias", rs.getString("alias"));
				map.put("ParentCode", rs.getString("parent_code"));
				map.put("Level", rs.getInt("level"));

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void testAddRegionInfos() {

		List<Map<String, Object>> list = findAllRegionInfo();

		JSONArray jsonArry = new JSONArray();

		List<RegionInfo> regionList = new ArrayList<RegionInfo>();

		for (Map<String, Object> map : list) {

			JSONObject jobj = new JSONObject(map);

			regionList.add(convertJSONObjectToRegionInfo(jobj));

		}

		for (RegionInfo regionInfo : regionList) {
			boolean add = lohAdminDao.addRegionInfo(regionInfo);
			if (add) {
				System.out.println("添加成功！");
			} else {
				System.out.println("添加失败！");
			}
		}
	}

	public RegionInfo convertJSONObjectToRegionInfo(JSONObject obj) {

		RegionInfo regionInfo = new RegionInfo();

		regionInfo.setGmtCreate((Date) obj.get("GmtCreate"));
		regionInfo.setGmtModified((Date) obj.get("GmtModified"));
		regionInfo.setRegionCode((String) obj.get("Code"));
		regionInfo.setRegionLevel((int) obj.get("Level"));
		regionInfo.setRegionName((String) obj.get("Alias"));

		return regionInfo;

	}

	// 初始化数据组，执行任务时向下递归查询子级数据
	public void testInit() {

		List<RegionInfo> regionList = lohAdminDao.findRegionInfoByLevel(1);

		Connection conn = JDBCUtil.getConnection();

		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (RegionInfo regionInfo : regionList) {

			// 处理每一个等级地区
			// 根据 parentCode 查询所有的子集
			// 将每个子集去数据库进行添加
			// 并且去查询子集的子集的子集数据去添加子集做递归

			// commit 一次
			try {

				diguifangfa(regionInfo, conn);

				conn.commit();

				System.out.println(regionInfo.getRegionName() + "\t数据添加完成");

			} catch (Exception e) {

				try {

					conn.rollback();

				} catch (SQLException e1) {

					e1.printStackTrace();

				}

				e.printStackTrace();
			}
		}
		
		// 释放数据库资源
		JDBCUtil.closeAll(null, null, conn);
	}

	public void diguifangfa(RegionInfo regionInfo, Connection conn) throws Exception {

		// 流程化处理的数据

		// 要做的事情

		// 1、 需要做添加操作数据库，抛出异常信息

		// 不做事务控制由顶层 conn 来控制提交回滚事务

		// 递归调用条件还有子集的数据或者 level < 5

		// 进行数据查询

		// 添加完再查询比较好做

		// 需要处理的数据
		List<Map<String, Object>> list = findRegionBy(regionInfo.getRegionCode());

		List<RegionInfo> regionList = new ArrayList<RegionInfo>();

		for (Map<String, Object> map : list) {

			JSONObject jobj = new JSONObject(map);

			RegionInfo reg = convertJSONObjectToRegionInfo(jobj);

			// 设置子集区域的父级区域 id
			reg.setParentRegionId(regionInfo.getRegionInfoId());

			regionList.add(reg);
		}

		// 循环保存所有子集， 这里的子集要添加父级ID
		for (RegionInfo reg : regionList) {

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

			PreparedStatement ps = null;
			ResultSet rs = null;

			// 设置自动提交事务为 false

			// 设置返回自动增长的 id
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setTimestamp(1, new Timestamp(reg.getGmtCreate().getTime()));
			ps.setTimestamp(2, new Timestamp(reg.getGmtModified().getTime()));
			ps.setString(3, reg.getRegionCode());
			ps.setString(4, reg.getRegionName());
			ps.setInt(5, reg.getRegionLevel());
			if (reg.getParentRegionId() == null) {
				ps.setNull(6, Types.INTEGER);
			} else {
				ps.setInt(6, reg.getParentRegionId());
			}

			int count = ps.executeUpdate();

			if (count <= 0) {

				throw new Exception(" 数据添加影响为 0 条 ");
			}

			rs = ps.getGeneratedKeys();

			// 设置记录自动增长 id
			while (rs.next()) {
				reg.setRegionInfoId(rs.getInt(1));
			}

			// 释放数据库资源
			JDBCUtil.closeAll(rs, ps, null);
			
			// 单个 Region 添加完毕，向下递归数据
			if (reg.getRegionLevel() < 5) {

				// 加载子集数据
				diguifangfa(reg, conn);
			}
		}
	}
}
