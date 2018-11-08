/**
 * 
 */
package science.mrcuijt.loh.service.impl;

import java.util.List;

import science.mrcuijt.loh.dao.LohAdminDao;
import science.mrcuijt.loh.dao.impl.LohAdminDaoImpl;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.service.LohAdminService;

/**
 * @author Administrator
 *
 */
public class LohAdminServiceImpl implements LohAdminService {

	private LohAdminDao lohAdminDao = new LohAdminDaoImpl();

	public LohAdminDao getLohAdminDao() {
		return lohAdminDao;
	}

	public void setLohAdminDao(LohAdminDao lohAdminDao) {
		this.lohAdminDao = lohAdminDao;
	}

	/**
	 * 查询房屋类型是否存在的业务逻辑接口
	 * 
	 * @param houseType
	 * 
	 * @return
	 */
	@Override
	public boolean findLohHouseTypeByHouseType(String houseType) {

		LohHouseType lohHouseType = lohAdminDao.findLohHouseTypeByHouseType(houseType);

		// 返回函数值
		return lohHouseType == null ? false : true;
	}

	/**
	 * 添加房屋类型
	 * 
	 * @param lohHouseType
	 * @return
	 */
	@Override
	public boolean addLohHouseType(LohHouseType lohHouseType) {
		
		// 返回函数值
		return lohAdminDao.addLohHouseType(lohHouseType);
	}

	/**
	 * 查询房屋类型的业务逻辑接口
	 * 
	 * @return
	 */
	@Override
	public List<LohHouseType> findLohHouseTypeList() {
		return lohAdminDao.findLohHouseTypeList();
	}

	/**
	 * 根据房屋类型Id查询房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId) {
		return lohAdminDao.findLohHouseTypeByPrimaryKey(lohHouseTypeId);
	}

	/**
	 * 根据房屋类型Id查询房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public List<LohHouseInfo> findLohHouseInfoByLohHouseTypeId(Integer lohHouseTypeId) {
		return lohAdminDao.findLohHouseInfoByLohHouseTypeId(lohHouseTypeId);
	}
	
	

}
