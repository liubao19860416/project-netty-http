package com.saic.ebiz.vbox.stream.down;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.bean.CarCheckType;

/**
 * 下行数据包-查询车况信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年2月19日
 * 
 */
public class CarStatusDownStream extends DownStream {

    private static final long serialVersionUID = 6759834712964293632L;
    @Descriptor(value = "查询类别")
    private CarCheckType carCheckType;

    public CarStatusDownStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String commandName,
            CarCheckType carCheckType) {
        this(commandType, msgProperty, sn, msgNumber, msgBody, carCheckType);
        super.setCommandName(commandName);
    }

    public CarStatusDownStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody,
            CarCheckType carCheckType) {
        super(commandType, msgProperty, sn, msgNumber, msgBody);
        this.carCheckType = carCheckType;
    }

    public CarCheckType getCarCheckType() {
        return carCheckType;
    }

    public void setCarCheckType(CarCheckType carCheckType) {
        this.carCheckType = carCheckType;
    }

}
