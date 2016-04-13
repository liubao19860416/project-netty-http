package com.saic.ebiz.vbox.stream.bean;

/**
 * 查询类别枚举类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年2月19日
 * 
 */
public enum CarCheckType {
    ERRORCODE ("01", "查询故障码信息" ),    
    ONEKEYDETECTION ("02", "一键检测" ),    
    FULLCARSCAN ("03", "全车扫描" ),    
    DEFAULT("", "预留" ); 

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    private CarCheckType() {}
    
    private CarCheckType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static CarCheckType getByKey(String key) {
        CarCheckType[] os = CarCheckType.values();
        for (int i = 0; i < os.length; i++) {
            if (os[i].getKey().equals(key)) {
                return os[i];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getValue();
    }
}