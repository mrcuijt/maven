/**
 * 
 */
package science.mrcuijt.loh.dao;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;

/**
 * @author Administrator
 *
 */
public class LohDaoTest {

	LohDao lohDao = new LohDaoImpl();

	@Test
	public void addFileInfoList() {

		LohHouseInfo lohHouseInfo = lohDao.findLohHouseInfoByPrimaryKey(104);

		List<LohFileInfo> lohFileInfoList = lohDao.findLohFileInfoByLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());

		lohDao.addFileInfoList(lohHouseInfo, lohFileInfoList);

		System.out.println(JSON.toJSONString(lohFileInfoList, true));

	}

}
