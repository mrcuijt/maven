/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.comm.LohFileType;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;
import science.mrcuijt.loh.util.AppMD5Util;
import science.mrcuijt.loh.util.Constants;
import science.mrcuijt.loh.util.XSSUtil;

/**
 * @author Administrator
 *
 */
public class ReleaseHouseServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ReleaseHouseServlet.class);

	private LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.info("查询全部类型的房屋类型信息");
		// 查询加载房屋类型
		List<LohHouseType> lohHouseTypeList = lohService.findAllLohHouseType();

		LOG.info("查询地区级别为[{}]的地区信息", 1);
		// 加载地区信息
		List<RegionInfo> regionInfoList = lohService.findRegionInfoByLevel(1);

		// 添加房屋类型列表
		request.setAttribute("lohHouseTypeList", lohHouseTypeList);

		// 添加地区信息
		request.setAttribute("regionInfoList", regionInfoList);

		request.getRequestDispatcher("/WEB-INF/html/loh/lease/release_house.jsp").forward(request, response);
		LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/loh/lease/release_house.jsp\").forward(request, response)");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = LOG.isErrorEnabled();

		// 解决 POST 请求中文参数乱码问题
		request.setCharacterEncoding("UTF-8");

		String message = null;

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 房屋标题
		String houseTitle = null;
		// 房屋类型
		String houseType = null;
		// 房屋价格
		String housePrice = null;
		// 房屋地址
		String houseAddress = null;
		// 房屋所在省
		String province = null;
		// 房屋所在市
		String city = null;
		// 房屋所在县
		String county = null;
		// 联系人
		String contacts = null;
		// 联系电话
		String cellPhone = null;

		// 上传的文件列表
		List<FileItem> fileItemList = new ArrayList<>();

		// 涉及到文件上传

		// 使用 apache-common-io 完成

		String temppath = "";
		String filepath = "";

		// 设置文件的临时存放路径
		temppath = request.getSession().getServletContext().getRealPath(Constants.TEMP_DIR);

		// 设置文件的实际存放路径
		filepath = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_DIR);

		if (!new File(temppath).exists()) {
			LOG.info("if (!new File(temppath).exists()) [{}]", (!new File(temppath).exists()));
			new File(temppath).mkdirs();
		}

		if (!new File(filepath).exists()) {
			LOG.info("if (!new File(filepath).exists()) [{}]", (!new File(filepath).exists()));
			new File(filepath).mkdirs();
		}

		DiskFileItemFactory fileFactory = new DiskFileItemFactory();

		// Apache文件上传组件在解析上传数据中的每个字段内容时，需要临时保存解析出的数据，
		// 以便在后面进行数据的进一步处理（保存在磁盘特定位置或插入数据库）
		// 设置临界值，以字节为单位，默认为 10KB，如果超出将以临时文件的形式存储在磁盘中
		fileFactory.setSizeThreshold(4 * 1024);

		// 临时文件存放的地址
		fileFactory.setRepository(new File(temppath));

		// 创建文件上传对象
		ServletFileUpload upload = new ServletFileUpload(fileFactory);

		// 将每一个请求的参数封装成一个 FileItem 对象

		try {

			if (debug) {
				LOG.debug("解析 request 请求");
			}
			List<FileItem> fileItems = upload.parseRequest(request);

			Iterator<FileItem> iter = fileItems.iterator();

			while (iter.hasNext()) {

				FileItem item = (FileItem) iter.next();

				// 判断是否是普通文本字段
				if (item.isFormField()) {
					LOG.info("if (item.isFormField()) [{}]", (item.isFormField()));

					String name = item.getFieldName();
					String value = item.getString("UTF-8");

					value = XSSUtil.cleanXSS(value);

					if (debug) {
						LOG.debug("name: {} \t value: {}", name, value);
					}

					switch (name) {

					case "houseTitle": // 房屋标题
						houseTitle = value;
						break;
					case "houseType": // 房屋类型
						houseType = value;
						break;
					case "housePrice": // 房屋价格
						housePrice = value;
						break;
					case "houseAddress": // 房屋地址
						houseAddress = value;
						break;
					case "province": // 房屋所在省
						province = value;
						break;
					case "city": // 房屋所在市
						city = value;
						break;
					case "county": // 房屋所在县
						county = value;
						break;
					case "contacts": // 联系人
						contacts = value;
						break;
					case "cellPhone": // 联系方式
						cellPhone = value;
						break;
					}

				} else {
					LOG.info("if (item.isFormField()) [{}]", (item.isFormField()));
					fileItemList.add(item);
				}
			}
		} catch (FileUploadException e) {
			LOG.error("Process Form FieldItem Error FileUploadException", e);
		} catch (Exception e) {
			LOG.error("Exception", e);
		}

		// 数据校验处理

		boolean verifyResult = true;

		// 1、房屋类型 验证房屋类型是否存在
		// 2、房屋价格 转换为 BigDecimal
		Integer lohHouseTypeId = null;
		BigDecimal pushPrice = null;
		// 房屋所在省id
		Integer provinceId = null;
		// 房屋所在市id
		Integer cityId = null;
		// 房屋所在县id
		Integer countyId = null;

		try {
			if (houseType != null && houseType.trim().length() > 0) {
				LOG.info("if (houseType != null && houseType.trim().length() > 0) [{}]", (houseType != null && houseType.trim().length() > 0));
				houseType = houseType.trim();
				lohHouseTypeId = Integer.parseInt(houseType);
			}
		} catch (NumberFormatException e) {
			LOG.warn("Convert houseType [{}] has error",houseType , e);
		}

		try {
			if (housePrice != null && housePrice.trim().length() > 0) {
				LOG.info("if (housePrice != null && housePrice.trim().length() > 0) [{}]", (housePrice != null && housePrice.trim().length() > 0));
				housePrice = housePrice.trim();
				pushPrice = new BigDecimal(housePrice);
			}
		} catch (Exception e) {
			LOG.warn("Convert pushPrice [{}] has error",housePrice , e);
		}

		if (lohHouseTypeId == null) {
			verifyResult = false;
			message = "未选择房屋类型，请刷新后重试。";
		}

		// 验证房屋类型
		if (verifyResult) {

			// 查询房屋类型是否存在
			LOG.info("查询指定房屋类型[{}]是否存在", lohHouseTypeId);
			boolean existsHouseType = lohService.existsLohHouseTypeByPrimaryKey(lohHouseTypeId);

			if (!existsHouseType) {
				LOG.info("if (!existsHouseType) [{}]", (!existsHouseType));
				verifyResult = false;
				message = "请选择房屋类型后重试。";
			}
		}

		// 验证输入金额
		if (verifyResult) {

			if (pushPrice == null) {
				LOG.info("if (pushPrice == null) [{}]", (pushPrice == null));
				verifyResult = false;
				message = "请输入合法的金额。";
			}
		}

		// 验证房屋所在地区信息
		if (verifyResult) {

			try {

				provinceId = Integer.parseInt(province);

				cityId = Integer.parseInt(city);

				countyId = Integer.parseInt(county);

			} catch (NumberFormatException e) {
				LOG.warn("NumberFormatException", e);
				verifyResult = false;
				message = "请选择房屋地区后重试。";
			}
		}

		if (!verifyResult) {
			LOG.info("if (!verifyResult) [{}]", (!verifyResult));
			message = URLEncoder.encode(message, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/loh/lease/releaseHouse.do?message=" + message);
			LOG.info("response.sendRedirect(request.getContextPath() + \"/loh/lease/releaseHouse.do?message=\" + [{}])", URLDecoder.decode(message,"UTF-8"));
			return;
		}

		// 3、 文件上传处理
		LOG.info("文件上传处理");
		List<LohFileInfo> lohFileInfoList = new ArrayList<>();

		Iterator<FileItem> fileItemIterator = fileItemList.iterator();

		while (fileItemIterator.hasNext()) {

			FileItem item = fileItemIterator.next();

			String filename = item.getName();
			LOG.info("完整文件名：{}", filename);
			int index = filename.indexOf("\\");
			filename = filename.substring(index + 1);
			// 获取文件的大小
			long filesize = item.getSize();

			if (filename == null || filename.equals("")) {
				LOG.warn("完整文件名：{}", filename);
				continue;
			}

			String encodeFileName = URLEncoder.encode(filename, "UTF-8");

			encodeFileName = AppMD5Util.getMD5(filename) + filename.substring(filename.lastIndexOf("."));

			String timeznoe = System.currentTimeMillis() + "";

			// 为文件加上当前时间戳路径
			String relFilePath = filepath + "/" + timeznoe + "/";

			if (!new File(relFilePath).exists()) {
				LOG.info("if (!new File(relFilePath).exists()) [{}]", (!new File(relFilePath).exists()));
				new File(relFilePath).mkdirs();
			}

			File uploadFile = new File(relFilePath + encodeFileName);

			// 将文件写入到服务器中
			try {

				item.write(uploadFile);

				if (debug) {
					LOG.debug("{} 文件保存完毕。", filename);
					LOG.debug("文件大小为: {}", filesize);
				}

				// 添加 LohFileInfo 信息记录
				LohFileInfo lohFileInfo = new LohFileInfo();
				lohFileInfo.setFileLink(Constants.UPLOAD_DIR + "/" + timeznoe + "/" + encodeFileName);
				lohFileInfo.setLohFileTypeId(LohFileType.LohHousePicture);
				lohFileInfo.setFileTitle(filename);

				// 添加到文件列表中
				lohFileInfoList.add(lohFileInfo);

			} catch (Exception e) {
				LOG.error("FileUpload Fail Exception", e);
				e.printStackTrace();
			}
		}

		LohHouseInfo lohHouseInfo = new LohHouseInfo();

		lohHouseInfo.setGmtCreate(new Date());
		lohHouseInfo.setGmtModified(new Date());
		lohHouseInfo.setHouseTitle(houseTitle);
		lohHouseInfo.setLohHouseTypeId(lohHouseTypeId);
		lohHouseInfo.setPrice(pushPrice);
		lohHouseInfo.setHouseAddress(houseAddress);
		lohHouseInfo.setRegionInfoProvinceId(provinceId);
		lohHouseInfo.setRegionInfoCityId(cityId);
		lohHouseInfo.setRegionInfoCountyId(countyId);
		lohHouseInfo.setContacts(contacts);
		lohHouseInfo.setCellPhone(cellPhone);
		lohHouseInfo.setUserInfoId(userInfoId);
		lohHouseInfo.setPushDate(new Date());

		// boolean addLohHouseInfoResult = lohService.addLohHouseInfo(lohHouseInfo);

		LOG.info("保存房屋租赁信息和上传文件信息");
		boolean addLohHouseInfoResult = lohService.addLohHouseInfo(lohHouseInfo, lohFileInfoList);

		if (!addLohHouseInfoResult) {
			LOG.info("if (!addLohHouseInfoResult) [{}]", (!addLohHouseInfoResult));
			LOG.info("添加房屋租赁信息失败 [{}]", lohHouseInfo);
			LOG.info("查询全部类型的房屋类型信息");
			// 查询加载房屋类型
			List<LohHouseType> lohHouseTypeList = lohService.findAllLohHouseType();

			LOG.info("查询地区级别为[{}]的地区信息", 1);
			// 加载地区信息
			List<RegionInfo> regionInfoList = lohService.findRegionInfoByLevel(1);

			// 添加房屋类型列表
			request.setAttribute("lohHouseTypeList", lohHouseTypeList);

			// 添加地区信息
			request.setAttribute("regionInfoList", regionInfoList);
			message = "添加失败，请重试";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/html/loh/lease/release_house.jsp").forward(request, response);
			LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/loh/lease/release_house.jsp\").forward(request, response)");
			return;
		}
		LOG.info("房屋租赁信息[{}]添加成功", lohHouseInfo.getLohHouseInfoId());
		// 添加成功，跳转到发布房屋信息列表页
		message = "添加成功！";

		// 中文参数做加密
		message = URLEncoder.encode(message, "UTF-8");

		response.sendRedirect(request.getContextPath() + "/loh/lease/showReleaseHouse.do?id="
				+ lohHouseInfo.getLohHouseInfoId() + "&message=" + message);
		LOG.info("response.sendRedirect(request.getContextPath() + \"/loh/lease/showReleaseHouse.do?id=[{}]&message=[{}]);", lohHouseInfo.getLohHouseInfoId(), URLDecoder.decode(message,"UTF-8"));
	}

}
