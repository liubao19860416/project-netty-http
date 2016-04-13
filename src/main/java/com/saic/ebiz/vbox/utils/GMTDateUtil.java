package com.saic.ebiz.vbox.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 格式化GMT时区时间格式工具类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月23日
 * 
 */
public class GMTDateUtil {

    private static final Logger logger = LoggerFactory.getLogger(GMTDateUtil.class);
    public static final String GMT = "GMT";
    public static final String GMT_DATE_PATTERN = "yy-MM-dd-HH-mm-ss";
    public static final String GMT_DATE_SEPERATOR = "-";
    public static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    //public static final DateFormat GMT_DATEFORMAT = new SimpleDateFormat(GMT);
    
    /**
     * 以ddMMyyHHmmss格式来解析GMT0时间
     */
    public static Date parseGMT0Time(String timeStr) {
        return parseGMT0Time(GMT_DATE_PATTERN, timeStr);
    }

    public static Date parseGMT0Time(String format, String timeStr) {
        logger.info("解析GMT时间请求参数为:{}",timeStr);
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(GMT_TIMEZONE);
        try {
            return df.parse(timeStr);
        } catch (ParseException e) {
            logger.error("解析GMT时间异常...",e);
        }
        return null;
    }

    public static String formatGMT0Time() {
        return formatGMT0Time(GMT_DATE_PATTERN);
    }

    public static String formatGMT0Time(Date date) {
        return formatGMT0Time(GMT_DATE_PATTERN, date);
    }

    public static String formatGMT0Time(String format) {
        return formatGMT0Time(format, new Date());
    }

    public static String formatGMT0Time(String format, Date time) {
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(GMT_TIMEZONE);
        return df.format(time);
    }
    
}
