/*
 * Copyright (C), 2013-2015, 上海汽车集团股份有限公司
 * FileName: ChecksumException.java
 * Author:   hechao
 * Date:     2015年5月25日 下午3:21:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.saic.ebiz.vbox.exception;

/**
 * 校验码校验失败异常<br> 
 * 〈功能详细描述〉
 *
 * @author hechao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ChecksumException extends Exception {

	private static final long serialVersionUID = -5964198498534495683L;

	public ChecksumException() {}
	
	public ChecksumException(String msg) {
		super(msg);
	}
}
