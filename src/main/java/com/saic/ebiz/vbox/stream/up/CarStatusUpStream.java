package com.saic.ebiz.vbox.stream.up;

import java.util.List;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.CarStatus;

/**
 * 车身状态信息-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class CarStatusUpStream extends UpStream {

    private static final long serialVersionUID = -5021241447573390890L;

    // 数据状态标识
    @Descriptor(value="数据状态标识")
    private String statusFlag;
    // 灯状态掩码
    @Descriptor(value="灯状态掩码")
    private String lampMask;
    // 车辆状态列表
    @Descriptor(value="车辆状态列表")
    private List<CarStatus> carStatusList;
    
    public CarStatusUpStream(String commandType,
            String msgProperty, String sn, String msgNumber, String msgBody,
            String checkCode, byte[] dataStream, String commandFull,
            String statusFlag, String lampMask,
            List<CarStatus> carStatusList) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.statusFlag = statusFlag;
        this.lampMask = lampMask;
        this.carStatusList = carStatusList;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getLampMask() {
        return lampMask;
    }

    public void setLampMask(String lampMask) {
        this.lampMask = lampMask;
    }

    public List<CarStatus> getCarStatusList() {
        return carStatusList;
    }

    public void setCarStatusList(List<CarStatus> carStatusList) {
        this.carStatusList = carStatusList;
    }

}
