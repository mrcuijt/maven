/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.apache.log4j.Logger;

import science.mrcuijt.loh.comm.LohFileType;
import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;
import science.mrcuijt.loh.util.AppMD5Util;
import science.mrcuijt.loh.util.Constants;

/**
 * @author Administrator
 *
 */
public class UpdateReleaseHouseServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(UpdateReleaseHouseServlet.class);
	
	private LohService lohService = new LohServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 解决 POST 请求中文参数乱码问题
		request.setCharacterEncoding("UTF-8");

		String message = null;

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 房屋信息ID
		Integer lohHouseInfoId = null;

		// 房屋标题
		String houseTitle = null;
		// 房屋类型
		String houseType = null;
		// 房屋价格
		String housePrice = null;
		// 房屋地址
		String houseAddress = null;
		// 联系人
		String contacts = null;
		// 联系电话
		String cellPhone = null;

		// 上传的文件列表
		List<FileItem> fileItemList = new ArrayList<>();

		List<Integer> imageIdList = new ArrayList<>();
		
		// 涉及到文件上传

		// 使用 apache-common-io 完成

		String temppath = "";
		String filepath = "";

		// 设置文件的临时存放路径
		temppath = request.getSession().getServletContext().getRealPath(Constants.TEMP_DIR);

		// 设置文件的实际存放路径
		filepath = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_DIR);

		if (!new File(temppath).exists()) {
			new File(temppath).mkdirs();
		}

		if (!new File(filepath).exists()) {
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

			List<FileItem> fileItems = upload.parseRequest(request);

			Iterator<FileItem> iter = fileItems.iterator();

			while (iter.hasNext()) {

				FileItem item = (FileItem) iter.next();

				// 判断是否是普通文本字段
				if (item.isFormField()) {

					String name = item.getFieldName();
					String value = item.getString();
					System.out.println("name:" + name + "\t value:" + value);

					switch (name) {
					case "lohHouseInfoId":
						try {
							lohHouseInfoId = Integer.parseInt(item.getString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "houseTitle": // 房屋标题
						houseTitle = item.getString("UTF-8");
						break;
					case "houseType": // 房屋类型
						houseType = item.getString();
						break;
					case "housePrice": // 房屋价格
						housePrice = item.getString();
						break;
					case "houseAddress": // 房屋地址
						houseAddress = item.getString("UTF-8");
						break;
					case "contacts": // 联系人
						contacts = item.getString("UTF-8");
						break;
					case "cellPhone": // 联系方式
						cellPhone = item.getString();
						break;
					case "imageId":
						try {
							imageIdList.add(Integer.parseInt(item.getString()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}

				} else {

					fileItemList.add(item);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 数据校验处理

		boolean verifyResult = true;

		// 1、房屋类型 验证房屋类型是否存在
		// 2、房屋价格 转换为 BigDecimal
		Integer lohHouseTypeId = null;
		BigDecimal pushPrice = null;
		try {
			lohHouseTypeId = Integer.parseInt(houseType);
			pushPrice = new BigDecimal(housePrice);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (lohHouseInfoId == null) {
			verifyResult = false;
			message = "不存在的房屋信息，请刷新后重试。";
		}

		if (lohHouseTypeId == null) {
			verifyResult = false;
			message = "未选择房屋类型，请刷新后重试。";
		}

		// 查询房屋信息是否存在
		LohHouseInfo lohHouseInfo = null;
		if (verifyResult) {
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			if(lohHouseInfo == null) {
				verifyResult = false;
				message = "不存在的房屋信息，请刷新后重试。";
			}
		}
		
		// 判断是否是该用户发布的房屋信息
		if(verifyResult) {
			if(lohHouseInfo.getUserInfoId().intValue() != userInfoId.intValue()) {
				verifyResult = false;
				message = "当前用户没有权限修改该房屋信息。";
			}
		}

		if (verifyResult) {

			// 查询房屋类型是否存在
			boolean existsHouseType = lohService.existsLohHouseTypeByPrimaryKey(lohHouseTypeId);

			if (!existsHouseType) {
				verifyResult = false;
				message = "请选择房屋类型后重试。";
			}
		}

		if (verifyResult) {

			if (pushPrice == null) {
				verifyResult = false;
				message = "请输入合法的金额。";
			}
		}

		if (!verifyResult) {

			response.sendRedirect(request.getContextPath() + "/loh/lease/releaseHouse.do?message=" + message);
			return;
		}

		// 3、 文件上传处理
		List<LohFileInfo> lohFileInfoList = new ArrayList<>();

		Iterator<FileItem> fileItemIterator = fileItemList.iterator();

		while (fileItemIterator.hasNext()) {

			FileItem item = fileItemIterator.next();

			String filename = item.getName();
			System.out.println("完整文件名：" + filename);
			int index = filename.indexOf("\\");
			filename = filename.substring(index + 1);
			// 获取文件的大小
			long filesize = item.getSize();

			if (filename == null || filename.equals("")) {
				System.out.println("文件名为空。");
				continue;
			}

			String encodeFileName = URLEncoder.encode(filename, "UTF-8");

			encodeFileName = AppMD5Util.getMD5(filename) + filename.substring(filename.lastIndexOf("."));

			String timeznoe = System.currentTimeMillis() + "";
			
			// 为文件加上当前时间戳路径
			String relFilePath = filepath + "/" + timeznoe + "/";

			if (!new File(relFilePath).exists()) {
				new File(relFilePath).mkdirs();
			}
			
			File uploadFile = new File(relFilePath + encodeFileName);

			// 将文件写入到服务器中
			try {

				item.write(uploadFile);
				System.out.println(filename + "文件保存完毕。");
				System.out.println("文件大小为:" + filesize);

				// 添加 LohFileInfo 信息记录
				LohFileInfo lohFileInfo = new LohFileInfo();
				lohFileInfo.setFileLink(Constants.UPLOAD_DIR + "/" + timeznoe + "/" + encodeFileName);
				lohFileInfo.setLohFileTypeId(LohFileType.LohHousePicture);
				lohFileInfo.setFileTitle(filename);

				// 添加到文件列表中
				lohFileInfoList.add(lohFileInfo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 设置房屋信息记录更新的时间
		lohHouseInfo.setGmtModified(new Date());
		
		// 设置新的房屋标题
		lohHouseInfo.setHouseTitle(houseTitle);
		// 设置新的房屋类型
		lohHouseInfo.setLohHouseTypeId(lohHouseTypeId);
		// 设置新的房屋价格
		lohHouseInfo.setPrice(pushPrice);
		// 设置新的房屋地址
		lohHouseInfo.setHouseAddress(houseAddress);
		// 设置新的地区信息
		lohHouseInfo.setRegionInfoId(null);
		// 设置新的房屋联系人
		lohHouseInfo.setContacts(contacts);
		// 设置新的房屋联系电话
		lohHouseInfo.setCellPhone(cellPhone);
		// 设置新的发布时间 
		lohHouseInfo.setPushDate(new Date());

		// 更新房屋信息，以及房屋图片信息 
		boolean updateHouseInfoResult = lohService.updateHouseInfoResult(lohHouseInfo);
		
		// 更新房屋图片信息列表
		boolean updateLohFileInfoResult = lohService.updateLohFileInfo(lohHouseInfo,imageIdList,lohFileInfoList,request.getServletContext().getRealPath("/"));
		
		if (!updateHouseInfoResult) {
			message = "更新失败，请重试";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/html/loh/lease/release_house.jsp").forward(request, response);
			return;
		}

		// 添加成功，跳转到发布房屋信息列表页
		message = "更新成功！";

		// 中文参数做加密
		message = URLEncoder.encode(message, "UTF-8");

		response.sendRedirect(request.getContextPath() + "/loh/lease/showReleaseHouse.do?id="
				+ lohHouseInfo.getLohHouseInfoId() + "&message=" + message);

	}
}