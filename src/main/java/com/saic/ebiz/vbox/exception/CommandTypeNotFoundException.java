package com.saic.ebiz.vbox.exception;

/**
 * 自定义异常:请求commandType不存在/错误自定义异常
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月19日
 * 
 */
public class CommandTypeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2401713917615095223L;

    public CommandTypeNotFoundException() {
    }

    public CommandTypeNotFoundException(String msg) {
        super(msg);
    }
}
