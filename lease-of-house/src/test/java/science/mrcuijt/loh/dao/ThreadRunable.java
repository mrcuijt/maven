/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.message.TimestampMessage;

import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class ThreadRunable implements Runnable {

	RegionInfo regionInfo = null;

	ThreadRunable(RegionInfo regionInfo) {
		this.regionInfo = regionInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Connection conn = JDBCUtil.getConnection();

		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// commit 一次
		try {

			Date beginDate = new Date(System.currentTimeMillis());

			System.out.println(regionInfo.getRegionName() + "\t数据开始添加 \t" + String.format("%tF ", beginDate) + " "
					+ String.format("%tT", beginDate));

			AddThreadTest.diguifangfa(regionInfo, conn);

			conn.commit();

			// 耗时
			long times = System.currentTimeMillis() - beginDate.getTime();

			beginDate = new Date(System.currentTimeMillis());
			System.out.println(regionInfo.getRegionName() + "\t数据添加完成 \t" + String.format("%tF ", beginDate) + " "
					+ String.format("%tT", beginDate) + "\t" + "耗时毫秒 \t" + times + "秒 \t" + (times / 1000));

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();
		} finally {
			// 释放数据库资源
			JDBCUtil.closeAll(null, null, conn);
		}
	}
}
