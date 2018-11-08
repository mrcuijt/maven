/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import science.mrcuijt.loh.entity.RegionInfo;

/**
 * @author Administrator
 *
 */
public class ThreadRunableTest {
	
	public static void main(String[] args) {

		List<RegionInfo> regionList = AddThreadTest.lohAdminDao.findRegionInfoByLevel(1);

		List<Thread> threadList = new ArrayList<>();

		for (RegionInfo regionInfo : regionList) {

			ThreadRunable threadRunable = new ThreadRunable(regionInfo);

			Thread thread = new Thread(threadRunable);
			thread.start();
			threadList.add(thread);
		}
		
		for (Thread thread : threadList) {
			System.out.println(thread.getName());
		}
	}
}
