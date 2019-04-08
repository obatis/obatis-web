package com.sbatis.core.constant.type;

/**
 * 时间操作类型
 * @author JianXin
 *
 */
public enum DateInter {

	/**
	 * 默认，表示对日期不作处理
	 */
	NOT_HANDLE, 
	/**
	 * 表示处理为开始时间，格式为：yyyy-MM-dd 00:00:00
	 */
	BEGIN, 
	/**
	 * 表示处理为结束时间，格式为：yyyy-MM-dd 23:59:59
	 */
	END;
}
