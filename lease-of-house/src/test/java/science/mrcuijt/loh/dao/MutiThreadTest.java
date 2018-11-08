/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class MutiThreadTest extends Thread {

	@Override
	public void run() {
		
		List<RegionInfo> regionList = AddThreadTest.lohAdminDao.findRegionInfoByLevel(1);

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

				AddThreadTest.diguifangfa(regionInfo, conn);

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

}
