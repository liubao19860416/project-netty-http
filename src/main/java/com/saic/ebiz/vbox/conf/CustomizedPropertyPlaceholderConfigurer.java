package com.saic.ebiz.vbox.conf;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 统一配置读取的工具类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年11月16日
 * 
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static Logger logger = LoggerFactory.getLogger(CustomizedPropertyPlaceholderConfigurer.class);
    public static Map<String, String> ctxPropertiesMap;
    
    public static String getContextProperty(String name) {
        logger.warn("读取ucm配置信息:");
        for (Entry<String, String> entry : ctxPropertiesMap.entrySet()) {
            if(name.equals(entry.getKey())){
                logger.warn(entry.getKey() + "================>>>" + entry.getValue());
                break;
            }
        }
        return ctxPropertiesMap.get(name);
    }

}
