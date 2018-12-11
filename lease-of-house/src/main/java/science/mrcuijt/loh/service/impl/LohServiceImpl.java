/**
 * 
 */
package science.mrcuijt.loh.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.comm.LohConstants;
import science.mrcuijt.loh.dao.LohDao;
import science.mrcuijt.loh.dao.impl.LohDaoImpl;
import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;

/**
 * @author Administrator
 *
 */
public class LohServiceImpl implements LohService {

	private static final Logger LOG = LoggerFactory.getLogger(LohServiceImpl.class);
	
	private LohDao lohDao = new LohDaoImpl();

	public LohDao getLohDao() {
		return lohDao;
	}

	public void setLohDao(LohDao lohDao) {
		this.lohDao = lohDao;
	}

	/**
	 * 用户注册的业务逻辑接口
	 * 
	 * @param userInfo
	 * @param loginInfo
	 * @return
	 */
	@Override
	public boolean userRegister(UserInfo userInfo, LoginInfo loginInfo) {

		// 返回函数值
		return lohDao.saveUserInfoAndLoginInfo(userInfo, loginInfo);
	}

	/**
	 * 用户登录的业务逻辑接口
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@Override
	public LoginInfo userLogin(String userName, String password) {

		LoginInfo info = lohDao.findLoginInfo(userName, password);

		// 返回函数值
		return info;
	}

	/**
	 * 验证用户是否存在的业务逻辑接口
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public boolean existsUser(String userName) {

		LoginInfo loginInfo = lohDao.findLoginInfoByLoginName(userName);

		if (loginInfo != null) {

			// 返回函数值
			return true;
		}

		// 返回函数值
		return false;
	}

	/**
	 * 查询登录信息的业务逻辑接口
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public LoginInfo findLoginInfo(String userName) {

		// 返回函数值
		return lohDao.findLoginInfoByLoginName(userName);
	}

	/**
	 * 更新登录信息的业务逻辑接口
	 * 
	 * @param loginInfo
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public boolean updateLoginInfo(LoginInfo loginInfo) throws SQLException {

		return lohDao.updateLoginInfo(loginInfo);
	}

	/**
	 * 添加房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	@Override
	public boolean addLohHouseInfo(LohHouseInfo lohHouseInfo) {

		return lohDao.addLohHouseInfo(lohHouseInfo);
	}

	/**
	 * 添加房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @param lohFileInfoList
	 */
	@Override
	public boolean addLohHouseInfo(LohHouseInfo lohHouseInfo, List<LohFileInfo> lohFileInfoList) {

		if (lohFileInfoList == null || lohFileInfoList.size() == 0) {
			return lohDao.addLohHouseInfo(lohHouseInfo);
		}

		return lohDao.addLohHouseInfo(lohHouseInfo, lohFileInfoList);
	}

	/**
	 * 根据房屋信息主键查询房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public LohHouseInfo findLohHouseInfoByPrimaryKey(Integer lohHouseInfoId) {

		return lohDao.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
	}
	
	/**
	 * 删除指定的房屋文件信息记录与房屋信息记录的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @param webRootPath
	 * @return
	 */	
	@Override
	public boolean deleteLohHouseInfo(LohHouseInfo lohHouseInfo, String webRootPath) {
		
		// 查询当前房屋信息所有文件信息记录
		List<LohFileInfo> lohFileInfoList = lohDao.findLohFileInfoByLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());
		// 删除文件信息记录
		boolean deleteResult = lohDao.deleteFileInfoList(lohFileInfoList);
		
		if(deleteResult) {
			
			// 循环删除所有上传文件
			for (LohFileInfo lohFileInfo : lohFileInfoList) {
				
				String relPath = webRootPath + lohFileInfo.getFileLink();
				
				try {
					File file = new File(relPath);
					if (file.exists() && file.isFile()) {
						// 删除文件
						file.delete();
					}
					// 判断当前目录下还有其它文件没有
					File parentFile = file.getParentFile();
					if (parentFile.isDirectory() && parentFile.listFiles().length == 0) {
						// 删除目录
						parentFile.delete();
					}
				} catch (Exception e) {
					LOG.error("删除文件失败，文件目录为：" + relPath, e);
				}
			}
			
			try {
				deleteResult = lohDao.deleteLohHouseInfoByPrimaryKey(lohHouseInfo.getLohHouseInfoId());
			} catch (Exception e) {
				deleteResult = false;
				e.printStackTrace();
			}
		}
		// 返回函数值
		return deleteResult;
	}

	/**
	 * 查询用户信息的业务逻辑接口
	 * 
	 * @param userInfoId
	 * @return
	 */
	@Override
	public UserInfo findUserInfoByPrimaryKey(Integer userInfoId) {

		return lohDao.findUserInfoByPrimaryKey(userInfoId);
	}

	/**
	 * 查询用户发布的房屋信息列表的业务逻辑接口（分页查询）
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParam
	 * @return
	 */
	@Override
	public Map<String, Object> queryHouseInfoPagination(Integer pageIndex, Integer pageSize,
			Map<String, Object> queryParam) {

		// Map<String, Object> queryPagination = new HashMap<String, Object>();

		// 查询总记录数

		// 分页查询

		return lohDao.queryHouseInfoPagination(pageIndex, pageSize, queryParam);
	}

	/**
	 * 查询全部房屋类型列表的业务逻辑接口
	 * 
	 * @return
	 */
	@Override
	public List<LohHouseType> findAllLohHouseType() {
		return lohDao.findAllLohHouseType();
	}

	/**
	 * 根据地区级别查询地区信息的业务逻辑接口
	 * 
	 * @param regionLevel
	 * @return
	 */
	@Override
	public List<RegionInfo> findRegionInfoByLevel(Integer regionLevel) {

		if (regionLevel == LohConstants.getTopRegionLevel().intValue() && LohConstants.getRegionLimit() > 0) {
			return lohDao.findRegionInfoByLevel(regionLevel, LohConstants.getRegionLimit());
		}

		return lohDao.findRegionInfoByLevel(regionLevel);
	}

	/**
	 * 根据父级地区Id查询对应的子集地区信息的业务逻辑接口
	 * 
	 * @param parentRegionId
	 * @return
	 */
	@Override
	public List<RegionInfo> findRegionInfoByParentRegionId(Integer parentRegionId) {

		return lohDao.findRegionInfoByParentRegionId(parentRegionId);
	}
	
	/**
	 * 根据地区信息表主键查询地区信息表记录的业务逻辑接口
	 * 
	 * @param regionInfoId
	 * @return
	 */
	@Override
	public RegionInfo findRegionInfoByPrimaryKey(Integer regionInfoId) {
		
		return lohDao.findRegionInfoByPrimaryKey(regionInfoId);
	}

	/**
	 * 验证房屋类型是否存在的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public boolean existsLohHouseTypeByPrimaryKey(Integer lohHouseTypeId) {
		LohHouseType lohHouseType = lohDao.findLohHouseTypeByPrimaryKey(lohHouseTypeId);
		return lohHouseType == null ? false : true;
	}

	/**
	 * 根据房屋类型Id查询房屋类型的业务逻辑接口
	 * 
	 * @param lohHouseTypeId
	 * @return
	 */
	@Override
	public LohHouseType findLohHouseTypeByPrimaryKey(Integer lohHouseTypeId) {
		LohHouseType lohHouseType = lohDao.findLohHouseTypeByPrimaryKey(lohHouseTypeId);
		return lohHouseType;
	}

	/**
	 * 根据房屋信息 Id 查询房屋文件信息的业务逻辑接口
	 * 
	 * @param lohHouseInfoId
	 * @return
	 */
	@Override
	public List<LohFileInfo> findLohFileInfoByLohHouseInfoId(Integer lohHouseInfoId) {

		return lohDao.findLohFileInfoByLohHouseInfoId(lohHouseInfoId);
	}

	/**
	 * 根据房屋信息 Id 更新房屋信息的业务逻辑接口
	 * 
	 * @param lohHouseInfo
	 * @return
	 */
	@Override
	public boolean updateHouseInfoResult(LohHouseInfo lohHouseInfo) {

		return lohDao.updateHouseInfoResult(lohHouseInfo);
	}

	/**
	 * 根据房屋信息更新房屋文件信息的接口
	 * 
	 * @param lohHouseInfo
	 * @param imageIdList
	 * @param lohFileInfoList
	 * @param webRootPath 
	 * @return
	 */
	@Override
	public boolean updateLohFileInfo(LohHouseInfo lohHouseInfo, List<Integer> imageIdList,
			List<LohFileInfo> lohFileInfoList, String webRootPath) {

		// 更新房屋图片信息
		// 3 个列表
		// 1）新增的图片信息列表 lohFileInfoList
		// 新上传的图片信息就为新增的图片信息列表
		// 2）修改的图片信息列表
		// 暂时没有修改图片的功能实现
		// 3）删除的图片信息列表 imageIds
		// 删除的图片信息列表
		// 对比数据库中现有的文件信息列表
		// 从表单中获取提交的文件信息列表的 id
		List<LohFileInfo> deleteLohFileInfoList = new ArrayList<>();

		List<LohFileInfo> dbLohFileInfoList = lohDao
				.findLohFileInfoByLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());

		// 数据库中删除的文件列表
		deleteLohFileInfoList.addAll(dbLohFileInfoList);
		
		// 1、修改列表
		// 2、删除列表
		for (Integer imageId : imageIdList) {
			for (LohFileInfo lohFileInfo : dbLohFileInfoList) {
				if (imageId.intValue() == lohFileInfo.getLohFileInfoId()) {
					// 剩下得到的就是被删除的文件列表
					deleteLohFileInfoList.remove(lohFileInfo);
				}
			}
		}
		
		boolean res = lohDao.deleteFileInfoList(deleteLohFileInfoList);
		
		if (res) {

			for (LohFileInfo lohFileInfo : deleteLohFileInfoList) {
				
				String relPath = webRootPath + lohFileInfo.getFileLink();
				
				try {
					File file = new File(relPath);
					if (file.exists() && file.isFile()) {
						// 删除文件
						file.delete();
					}
					// 判断当前目录下还有其它文件没有
					File parentFile = file.getParentFile();
					if (parentFile.isDirectory() && parentFile.listFiles().length == 0) {
						// 删除目录
						parentFile.delete();
					}
				} catch (Exception e) {
					LOG.error("删除文件失败，文件目录为：" + relPath, e);
				}
			}
			
			res = lohDao.addFileInfoList(lohHouseInfo, lohFileInfoList);

		}
		// 一般的表达记录中原有的记录列表中包含数据库中的 id
		// 新增的记录没有 id
		// 原有的现在删除了的当前提交的列表中数据库中对比后，提交列表中不存在的就是删除的列表

		return res;
	}

}
