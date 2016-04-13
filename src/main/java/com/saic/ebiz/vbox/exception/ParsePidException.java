package com.saic.ebiz.vbox.exception;

/**
 * 解析PID出错
 * @author hechao
 *
 */
public class ParsePidException extends RuntimeException {

	private static final long serialVersionUID = 4746827606554346271L;

	public ParsePidException() {}
	
	public ParsePidException(String msg) {
		super(msg);
	}
}
