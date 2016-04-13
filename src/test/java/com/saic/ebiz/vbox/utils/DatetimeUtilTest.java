package com.saic.ebiz.vbox.utils;

import java.sql.Timestamp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间格式化工具类测试单元
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
@SuppressWarnings("unused")
public class DatetimeUtilTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DatetimeUtilTest.class);
    
    @Test
    public void testMain() throws Exception {
        Timestamp weekPlus = DatetimeUtil.weekPlus(DatetimeUtil.createSpecialDateTime(DatetimeUtil.currentTimestamp(), null, 23, 59, 59), 2);
        System.out.println(DatetimeUtil.formatDate(weekPlus));
        
        String firstDayStr = DatetimeUtil.getNextWeekFirstDayStr(DatetimeUtil.currentTimestamp(), DatetimeUtil.DATETIME_PATTERN);
        System.out.println(firstDayStr);
        
        firstDayStr = DatetimeUtil.getNextWeekFirstDayStr(DatetimeUtil.dayPlus(DatetimeUtil.currentTimestamp(),3), DatetimeUtil.DATETIME_PATTERN);
        System.out.println(firstDayStr);
        
        System.out.println(DatetimeUtil.currentTimestamp());
        System.out.println(DatetimeUtil.millSecondPlus(DatetimeUtil.currentTimestamp(), 2000));
        System.out.println(DatetimeUtil.secondPlus(DatetimeUtil.currentTimestamp(), 10));
        System.out.println(DatetimeUtil.minitePlus(DatetimeUtil.currentTimestamp(), 1));
        System.out.println(DatetimeUtil.hourPlus(DatetimeUtil.currentTimestamp(), -1));
    }
    
}
