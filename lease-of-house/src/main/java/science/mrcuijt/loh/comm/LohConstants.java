/**
 * 
 */
package science.mrcuijt.loh.comm;

/**
 * @author Administrator
 *
 */
public class LohConstants {

	// 系统变量 接口返回的成功编码
	public static final int SUCCESS_CODE = 200;

	// 系统变量 接口返回的成功消息
	public static final String SUCCESS_MESSAGE = "SUCCESS";

	// 系统变量 接口返回的失败编码
	public static final int ERROR_CODE = 500;

	// 系统变量 查询地区级别为 1 的地区时限制地区返回条数
	private static Integer topRegionLevel = 1;

	// 系统变量 查询地区限制返回条数
	private static Integer regionLimit = -1;

	// 系统变量 系统默认省份 id
	private static Integer provinceId = -1;

	public static Integer getTopRegionLevel() {
		return topRegionLevel;
	}

	public static void setTopRegionLevel(Integer topRegionLevel) {
		LohConstants.topRegionLevel = topRegionLevel;
	}

	public static Integer getRegionLimit() {
		return regionLimit;
	}

	public static void setRegionLimit(Integer regionLimit) {
		LohConstants.regionLimit = regionLimit;
	}

	public static Integer getProvinceId() {
		return provinceId;
	}

	public static void setProvinceId(Integer provinceId) {
		LohConstants.provinceId = provinceId;
	}

}
