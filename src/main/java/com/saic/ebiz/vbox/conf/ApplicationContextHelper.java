package com.saic.ebiz.vbox.conf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 容器帮助类
 * 
 * @author Liubao
 * @2015年9月25日
 *
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware{
	
	private static ApplicationContext appCtx;  
	
    /** 
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。 
     */  
    public void setApplicationContext(ApplicationContext applicationContext ){  
        ApplicationContextHelper.appCtx = applicationContext;  
    }
    
    /** 
     * 这是一个便利的方法，帮助我们快速得到一个BEAN 
     * @param beanName bean的名字 
     * @return 返回一个bean对象 
     */  
    public static Object getBean(String beanName ) {  
        return appCtx.getBean( beanName );  
    }  
    
    /** 
     * 这是一个便利的方法，帮助我们快速得到一个BEAN 
     * @param beanName bean的名字 
     * @return 返回一个bean对象 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object getBean(Class clazz) {  
        return appCtx.getBean(clazz);  
    } 
    
    
    //========================新添加部分===================================
    
    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return appCtx;
    }

    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean2(String beanName) {
        checkApplicationContext();
        return (T) appCtx.getBean(beanName);
    }

    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean2(Class<?> clazz) {
        checkApplicationContext();
        return (T) appCtx.getBean(clazz);
    }

    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        appCtx = null;
    }

    private static void checkApplicationContext() {
        if (appCtx == null) {
            throw new IllegalStateException("ApplicationContext未注入,请在spring配置文件中中注入该bean");
        }
    }
    
    
    
}
