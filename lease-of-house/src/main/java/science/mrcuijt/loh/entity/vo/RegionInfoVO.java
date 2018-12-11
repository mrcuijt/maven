/**
 * 
 */
package science.mrcuijt.loh.entity.vo;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public class RegionInfoVO<E> {

	// 状态编号
	private int status = 0;

	// 状态信息
	private String message = null;

	// RegionInfo 信息集合
	private List<E> result = null;

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

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

}
