package com.saic.ebiz.vbox.exception;

/**
 * 保存MongoDB失败
 * @author hechao
 *
 */
public class MongoSaveException extends RuntimeException {

	private static final long serialVersionUID = 1388783824725564944L;

	public MongoSaveException() {}
	
	public MongoSaveException(String msg) {
		super(msg);
	}
}
