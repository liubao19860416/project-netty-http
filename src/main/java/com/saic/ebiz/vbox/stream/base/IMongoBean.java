package com.saic.ebiz.vbox.stream.base;

import com.mongodb.DBObject;

/**
 * MongoDB存储抽象接口
 */
public interface IMongoBean {

    public DBObject toDbObject();
}
