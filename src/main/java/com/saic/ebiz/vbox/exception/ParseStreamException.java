package com.saic.ebiz.vbox.exception;

/**
 * 解析数据流中数据异常
 * @author hechao
 *
 */
public class ParseStreamException extends RuntimeException {

	private static final long serialVersionUID = -2401713917615095223L;

	public ParseStreamException() {}
	
	public ParseStreamException(String msg) {
		super(msg);
	}
}
