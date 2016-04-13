package com.saic.ebiz.vbox.exception;

/**
 * 自定义异常:请求参数为空自定义异常
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月19日
 * 
 */
public class NullRequestParamException extends RuntimeException {

    private static final long serialVersionUID = -2401713917615095223L;

    public NullRequestParamException() {
    }

    public NullRequestParamException(String msg) {
        super(msg);
    }
}
