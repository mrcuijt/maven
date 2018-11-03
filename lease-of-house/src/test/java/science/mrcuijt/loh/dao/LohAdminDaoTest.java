/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.dao.impl.LohAdminDaoImpl;
import science.mrcuijt.loh.entity.LohHouseType;

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

}
