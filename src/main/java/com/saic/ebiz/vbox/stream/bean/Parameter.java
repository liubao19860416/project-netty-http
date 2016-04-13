package com.saic.ebiz.vbox.stream.bean;

import com.saic.ebiz.vbox.conf.Descriptor;

/**
 * 查询参数详情信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月29日
 * 
 */
public class Parameter extends MessageEntity {

    // 参数ID
    @Descriptor(value = "参数ID")
    private String id;
    // 参数ID的byte形式
    @Descriptor(value = "参数ID的byte形式")
    private byte[] idBytes;

    // 参数长度
    @Descriptor(value = "参数长度")
    private Integer length;
    // 参数值
    @Descriptor(value = "参数值")
    private String value;
    // 参数值Byte格式
    @Descriptor(value = "参数值Byte格式")
    private byte[] valueBytes;

    /**
     * 下行查询使用下面2个即可
     * @param id
     * @param idBytes
     */
    public Parameter(String id, byte[] idBytes) {
        super();
        this.id = id;
        this.idBytes = idBytes;
    }

    /**
     * 上行数据上报可以使用
     * @param id
     * @param idBytes
     * @param length
     * @param value
     * @param valueBytes
     */
    public Parameter(String id, byte[] idBytes, Integer length, String value,
            byte[] valueBytes) {
        super();
        this.id = id;
        this.idBytes = idBytes;
        this.length = length;
        this.value = value;
        this.valueBytes = valueBytes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getIdBytes() {
        return idBytes;
    }

    public void setIdBytes(byte[] idBytes) {
        this.idBytes = idBytes;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getValueBytes() {
        return valueBytes;
    }

    public void setValueBytes(byte[] valueBytes) {
        this.valueBytes = valueBytes;
    }

}
