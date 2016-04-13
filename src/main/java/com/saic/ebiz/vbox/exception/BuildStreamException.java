package com.saic.ebiz.vbox.exception;

/**
 * 创建Stream失败异常
 * @author hechao
 *
 */
public class BuildStreamException extends RuntimeException {

	private static final long serialVersionUID = -6087294993954658738L;

	public BuildStreamException() {}
	
	public BuildStreamException(String msg) {
		super(msg);
	}
}
