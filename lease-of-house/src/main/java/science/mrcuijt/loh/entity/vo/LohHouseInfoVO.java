/**
 * 
 */
package science.mrcuijt.loh.entity.vo;

import science.mrcuijt.loh.util.PageUtil;

/**
 * @author Administrator
 *
 */
public class LohHouseInfoVO {

	// 状态编号
	private int status = 0;

	// 状态信息
	private String message = null;

	// LohHouseInfo 房屋租赁信息分页列表
	private PageUtil result = null;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PageUtil getResult() {
		return result;
	}

	public void setResult(PageUtil result) {
		this.result = result;
	}

}
