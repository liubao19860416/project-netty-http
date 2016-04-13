package com.saic.ebiz.vbox.stream.base;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;
import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.utils.DatetimeUtil;
import com.saic.ebiz.vbox.utils.StreamUtils;

/**
 * 数据包基本数据格式抽象类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月13日
 * 
 */
public abstract class AbstractStream implements IMongoBean,Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractStream.class);
    private static final long serialVersionUID = -512671838900075419L;

    // 命令前后缀/标识位
    @Descriptor(value="命令前后缀/标识位")
    private String markBit;
    // 命令字/命令ID
    @Descriptor(value="命令字/命令ID")
    private String commandType;
    //消息属性
    @Descriptor(value="消息属性")
    private String msgProperty;
    // 设备ID/sn
    @Descriptor(value="设备ID/sn")
    private String sn;
    // 消息流水号
    @Descriptor(value="消息流水号")
    private String msgNumber;
    // 数据体
    @Descriptor(value="数据体")
    private String msgBody;
    // 校验码
    @Descriptor(value="校验码")
    private String checkCode;
    
    // 数据流 
    @Descriptor(value="数据流")
    private byte[] dataStream;
    
    //补充字段信息
    // 全命令
    @Descriptor(value="全命令")
    private String commandFull;
    // 命令名称(自定义字段)
    @Descriptor(value="命令名称(自定义字段)")
    private String commandName;
    // 记录创建时间
    @Descriptor(value="记录创建时间")
    private Timestamp createdDatetime;

    public AbstractStream(String markBit, String commandType,
            String msgProperty, String sn, String msgNumber, String msgBody,
            String checkCode, byte[] dataStream, String commandFull,
            String commandName) {
        this(markBit, commandType, msgProperty, sn, msgNumber, msgBody,
                checkCode, dataStream, commandFull);
        this.commandName = commandName;
    }

    public AbstractStream(String markBit, String commandType,
            String msgProperty, String sn, String msgNumber, String msgBody,
            String checkCode, byte[] dataStream, String commandFull) {
        this(commandType, msgProperty, sn, msgNumber, msgBody, checkCode);
        this.markBit = markBit;
        this.dataStream = dataStream;
        this.commandFull = commandFull;
    }
    
    public AbstractStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode) {
        this();
        this.commandType = commandType;
        this.commandName = StreamConstant.commandTypeMappingNameMap.get(commandType);
        this.msgProperty = msgProperty;
        this.sn = sn;
        this.msgNumber = msgNumber;
        this.msgBody = msgBody;
        this.checkCode = checkCode;
    }
    
    public AbstractStream() {
        super();
        this.createdDatetime = DatetimeUtil.currentTimestamp();
    }
    
    public Timestamp getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
    
    public String getMarkBit() {
        return markBit;
    }

    public void setMarkBit(String markBit) {
        this.markBit = markBit;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public byte[] getDataStream() {
        return dataStream;
    }

    public void setDataStream(byte[] dataStream) {
        this.dataStream = dataStream;
    }

    public String getCommandFull() {
        return commandFull;
    }

    public void setCommandFull(String commandFull) {
        this.commandFull = commandFull;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
    
    public String getMsgProperty() {
        return msgProperty;
    }

    public void setMsgProperty(String msgProperty) {
        this.msgProperty = msgProperty;
    }

    public String getMsgNumber() {
        return msgNumber;
    }

    public void setMsgNumber(String msgNumber) {
        this.msgNumber = msgNumber;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
    
    /**
     * 转换为DBObject对象
     */
    @Override
    public DBObject toDbObject() {
        DBObject bean2dbObject =null;
        try {
            logger.info("转换为DBObject对象开始...");
            bean2dbObject = StreamUtils.bean2DBObject(this);
            logger.info("转换为DBObject对象成功结束...");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            bean2dbObject=null;
            logger.error("转换为DBObject对象异常...",e);
        }
        return bean2dbObject;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
