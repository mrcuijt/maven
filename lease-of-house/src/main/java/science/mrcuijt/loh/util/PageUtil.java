/**
 * 
 */
package science.mrcuijt.loh.util;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class PageUtil<E> {

	// 当前页面序号
	private int pageIndex = 0;

	// 当前页面大小
	private int pageSize = 0;

	// 总页数
	private int totalPage = 0;

	// 总记录数
	private int totalRecord = 0;

	// 当前页列表
	private List<E> page = null;

	// 查询条件
	private Map<String, Object> queryParam = null;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<E> getPage() {
		return page;
	}

	public void setPage(List<E> page) {
		this.page = page;
	}

}
