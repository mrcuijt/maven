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
import java.util.concurrent.ThreadFactory;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import science.mrcuijt.loh.dao.impl.LohAdminDaoImpl;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class AddThreadTest {

	static LohAdminDao lohAdminDao = new LohAdminDaoImpl();

	private static final DruidDataSource DB_SOURCE_1 = new DruidDataSource();

	static {

		String DRVIER = "com.mysql.jdbc.Driver";

		String URL = "jdbc:mysql://localhost:3306/region?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

		String USER = "root";

		String PASSWORD = "root12";

		DB_SOURCE_1.setUsername(USER);
		DB_SOURCE_1.setPassword(PASSWORD);
		DB_SOURCE_1.setUrl(URL);

	}

	// 1、查询所有级别为 1 的 region 信息
	public List<Map<String, Object>> findAllRegionInfo() {

		List<Map<String, Object>> list = new ArrayList<>();

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

			conn = DB_SOURCE_1.getConnection();

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
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}

		System.out.println(JSON.toJSONString(list));

		return list;
	}

	public static List<Map<String, Object>> findRegionBy(String key) {

		List<Map<String, Object>> list = new ArrayList<>();

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

			conn = DB_SOURCE_1.getConnection();

			ps = conn.prepareStatement(sql);

			ps.setString(1, key);

			rs = ps.executeQuery();

			while (rs.next()) {

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
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
		}
		return list;
	}

	public List<RegionInfo> newFindRegionBy(String key) {

		List<RegionInfo> list = new ArrayList<>();

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

			conn = DB_SOURCE_1.getConnection();

			ps = conn.prepareStatement(sql);

			ps.setString(1, key);

			rs = ps.executeQuery();

			while (rs.next()) {

				RegionInfo regionInfo = new RegionInfo();
				regionInfo.setGmtCreate(rs.getTimestamp("gmt_create"));
				regionInfo.setGmtModified(rs.getTimestamp("gmt_modified"));
				regionInfo.setRegionCode(rs.getString("code"));
				regionInfo.setRegionName(rs.getString("alias"));
				regionInfo.setRegionLevel(rs.getInt("level"));

				list.add(regionInfo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeAll(rs, ps, conn);
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

	public static RegionInfo convertJSONObjectToRegionInfo(JSONObject obj) {

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

	public static void diguifangfa(RegionInfo regionInfo, Connection conn) throws Exception {

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

	public void testAll() {

		List<RegionInfo> regionList = lohAdminDao.findRegionInfoByLevel(1);

		// region_code 作为 map 的 key 存储一个 list
		// Map<String, List<RegionInfo>> mapLevel1 = new HashMap<>();

		Map<String, List<RegionInfo>> mapLevel2 = new HashMap<>();

		Map<String, List<RegionInfo>> mapLevel3 = new HashMap<>();

		Map<String, List<RegionInfo>> mapLevel4 = new HashMap<>();

		Map<String, List<RegionInfo>> mapLevel5 = new HashMap<>();

//		RegionInfo reg1 = regionList.get(0);
//
//		List<RegionInfo> reg2List = newFindRegionBy(reg1.getRegionCode());
//
//		RegionInfo reg2 = reg2List.get(0);
//
//		List<RegionInfo> reg3List = newFindRegionBy(reg2.getRegionCode());
//
//		RegionInfo reg3 = reg3List.get(0);
//
//		List<RegionInfo> reg4List = newFindRegionBy(reg3.getRegionCode());
//
//		RegionInfo reg4 = reg4List.get(0);
//
//		List<RegionInfo> reg5List = newFindRegionBy(reg4.getRegionCode());

		
		for (RegionInfo regionInfo : regionList) {
			List<RegionInfo> reg2List = newFindRegionBy(regionInfo.getRegionCode());
			mapLevel2.put(regionInfo.getRegionCode(), reg2List);
			// 查询所有的二级数据就需要 1 分钟时间
			// 查询三级数据四级数据就会更多的循环和查询需要的时间也就会更长
			// 做多线程处理吧
//			for (RegionInfo regionInfo2 : regionList) {
//				List<RegionInfo> reg3List = newFindRegionBy(regionInfo2.getRegionCode());
//				mapLevel3.put(regionInfo2.getRegionCode(), reg3List);
//			}
			// 做多线程就不一定能会到主线程了。
			// 所有任务都在后台线程，独立执行，互不干扰？
			// 多线程适合做异步事件通知。
			
			// 定义好一个线程类，给线程类固定的方法。类似于递归但是用过线程来更好的利用 CPU 资源了。
			// 创建多个线程对象，传入不同的参数，执行同样的流程操作。
			// 也就是说不用很麻烦，使用现有的方法，封装到 Thread 中。同步执行。
			// 考虑内存是否够用。
			
		}
		
		System.out.println(JSON.toJSONString(mapLevel2, true));
		System.out.println(JSON.toJSONString(mapLevel3, true));
		
		RegionInfo reg1 = regionList.get(0);

		List<RegionInfo> reg2List = newFindRegionBy(reg1.getRegionCode());

		RegionInfo reg2 = reg2List.get(0);

		List<RegionInfo> reg3List = newFindRegionBy(reg2.getRegionCode());

		RegionInfo reg3 = reg3List.get(0);

		List<RegionInfo> reg4List = newFindRegionBy(reg3.getRegionCode());

		RegionInfo reg4 = reg4List.get(0);

		List<RegionInfo> reg5List = newFindRegionBy(reg4.getRegionCode());
		
		// 第一层级方法 region_code

		// 下层的放 parent_code

		List<RegionInfo> regionLists = new ArrayList<>();

	}
	// 1、 主线程中启用线程池，并等待线程执行完毕后才退出

	// 2、 每个线程内开启自己的事务管理

}
