package com.saic.ebiz.vbox.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

/**
 * MongoDB访问工具类
 */
//@Component("mongoDbUtil")
//@Scope("singleton")
public class MongoDBUtil {
    private static Logger logger = LoggerFactory.getLogger(MongoDBUtil.class);
    
    /** mongo单例对象 根据官方文档mongojava是线程安全的 */
    private static MongoClient mongoClient;

    private DB db;
    private String defaultDbName;
    private String defaultCollName;
    private String mongoServerUrl;
    private String connectionPerHost;
    private String connectionMultiplier;
    private String socketTimeout;
    private String maxWaitTime;
    private String connectionTimeout;

    private MongoDBUtil() {
    }

    public void init() {
        logger.info(".init start...");
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        builder.maxWaitTime(Integer.valueOf(maxWaitTime));
        builder.connectTimeout(Integer.valueOf(connectionTimeout));
        builder.socketTimeout(Integer.valueOf(socketTimeout));
        builder.connectionsPerHost(Integer.valueOf(connectionPerHost));
        builder.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(connectionMultiplier));
        MongoClientOptions clientOptions = builder.build();
        try {
            List<ServerAddress> list = new ArrayList<ServerAddress>();
            if (StringUtils.isNotEmpty(mongoServerUrl)) {
                String[] urls = StringUtils.split(mongoServerUrl, ",");
                for (String url : urls) {
                    String[] host = StringUtils.split(url, ":");
                    ServerAddress serverAddress = new ServerAddress(host[0],Integer.valueOf(host[1]));
                    list.add(serverAddress);
                }
            }
            mongoClient = new MongoClient(list, clientOptions);
            mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
            //getColl().createIndex(new BasicDBObject("cityCode", 1));
            //getColl().createIndex(new BasicDBObject("queryInfo", -1));

            db = mongoClient.getDB(this.defaultDbName);
            logger.info(".init finished.");
        } catch (Exception ex) {
            logger.error(".init error!!", ex);
        }
    }
    
    /**
     * 返回mongo连接池
     */
    public static MongoClient getMongoClient() {
        logger.info(".getMongoClient IS executing...");
        return mongoClient;
    }
    
    public DB getDB() {
        return this.getDB(this.defaultDbName);
    }
    
    /**
     * 数据库一般不变更
     */
    private DB getDB(String dbName) {
        logger.info(".getDB(String) start;dbName={}",dbName);
        if(StringUtils.isBlank(dbName)){
            throw new RuntimeException("dbName IS null!!!");
        }
        if(this.db==null||!dbName.equalsIgnoreCase(this.db.getName())){
            this.db = mongoClient.getDB(dbName);
        }
        if(this.db==null){
            throw new RuntimeException("DB IS null ! Please ensure the dbName["+dbName+"]is right!");
        }
        logger.info(".getDB(String).[DB.hashCode:{},DB.toString:{}]",db.hashCode(),db.toString());
        logger.info(".getDB(String) finished...");
        return this.db;
    }

    /**
     * 获取默认collection
     */
    public DBCollection getColl() {
        return this.getColl(this.defaultCollName);
    }

    /**
     * 获取指定collName名称的collection
     */
    public DBCollection getColl(String collName) {
        return this.getColl(this.defaultDbName,collName);
    }
    
    /**
     * 获取指定数据库dbName的指定collName名称的collection对象 - 指定Collection
     */
    public DBCollection getColl(String dbName,String collName) {
        logger.info(".getColl(String,String) IS executing...[dbName={},collName={}]",dbName,collName);
        logger.info(".getColl executing...");
        if (StringUtils.isBlank(dbName)||StringUtils.isBlank(collName)) {
            return null;
        }
        DBCollection collection = this.getDB(dbName).getCollection(collName);
        logger.info(".getColl finished.");
        return collection;
    }

    /**
     * 插入单个数据
     */
    public void insert(DBObject dbObject) {
        this.insert(dbObject, this.defaultCollName);
    }

    /**
     * 插入单个数据
     */
    public void insert(DBObject dbObject, String collName) {
        if(dbObject==null){
            logger.error(".insert(DBObject,String) DBObject IS NULL...");
            return;
        }
        logger.info(".insert(DBObject,String) IS executing...");
        this.getColl(collName).insert(dbObject);
        logger.info(".insert finished.");
    }

    /**
     * 插入多个数据
     */
    public void insert(List<DBObject> dbObjects) {
        this.insert(dbObjects, this.defaultCollName);
    }

    /**
     * 插入多个数据
     */
    public void insert(List<DBObject> dbObjects, String collName) {
        logger.info(".insert(List<DBObject>,String) IS executing...");
        this.getColl(collName).insert(dbObjects);
        logger.info(".insert finished.");
    }
    
    /**
     * 查询指定DB下dbName的所有集合(表)名
     */
    public List<String> getAllCollNames(String dbName) {
        logger.info(".getAllCollNames executing...");
        Set<String> colls = this.getDB(dbName).getCollectionNames();
        List<String> _list = new ArrayList<String>(colls);
        logger.info(".getAllCollNames finished.");
        return _list;
    }
    
    /**
     * 查询默认DB下的所有集合(表)名
     */
    public List<String> getAllCollNames() {
        return getAllCollNames(this.defaultDbName);
    }
    
    /**
     * 获取所有数据库名称列表
     */
    public List<String> getAllDBNames() {
        logger.info(".getAllDBNames executing...");
        List<String> list = mongoClient.getDatabaseNames();
        logger.info(".getAllDBNames finished.");
        return list;
    }
    
    /**
     * 删除一个数据库
     */
    public void dropDB(String dbName) {
        logger.info(".dropDB executing...");
        this.getDB(dbName).dropDatabase();
        logger.info(".dropDB sinished.");
    }
    
    /**
     * 删除默认数据库的collName集合(表)
     */
    public void dropColl(String collName) {
        this.dropColl(this.defaultDbName, collName);
    }
    
    /**
     * 删除指定数据库dbName的指定集合collName
     */
    public void dropColl(String dbName, String collName) {
        logger.info(".dropColl executing...");
        this.getColl(dbName, collName).drop();
        logger.info(".dropColl sinished.");
    }
    
    /** 
     * 统计默认数据库的指定集合的个数 
     */
    public long getCount(String collName) {
        return this.getCount(this.defaultDbName, collName);
    }
    
    /** 
     * 统计指定数据库的指定集合的个数 
     */
    public long getCount(String dbName,String collName) {
        logger.info(".getCount executing...");
        long count = this.getColl(dbName,collName).count();
        logger.info(".getCount sinished.");
        return count;
    }
    
    /**
     * 关闭MongoClient
     */
    public void close() {
        logger.info(".close start");
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
        logger.info(".close end");
    }
    
    /**
     * 单个条件查询(默认数据库和指定集合表)
     */
    public DBCursor query(String collName,String key, String value) {
        return query(this.defaultDbName, collName, key, value);
    }
    /**
     * 单个条件查询(指定数据库和指定集合表)
     */
    public DBCursor query(String dbName,String collName,String key, String value) {
        logger.info(".query start");
        DBCursor dbCursor = null;
        try {
            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put(key, value);
            dbCursor = getColl(dbName,collName).find(basicDBObject);
        } catch (Exception e) {
            logger.error(".query error " + e);
        }
        logger.info(".query end");
        return dbCursor;
    }

    /**
     * 多条件查询
     */
    public DBCursor query(BasicDBList list) {
        logger.info(".query start");
        DBCursor dbCursor = null;
        try {
            BasicDBObject searchCond = new BasicDBObject();
            searchCond.put("$and", list);
            dbCursor = getColl().find(searchCond);
        } catch (Exception e) {
            logger.error(".query error " + e);
        }
        logger.info(".query end");
        return dbCursor;
    }

    public void setDefaultDbName(String defaultDbName) {
        this.defaultDbName = defaultDbName;
    }

    public void setDefaultCollName(String defaultCollName) {
        this.defaultCollName = defaultCollName;
    }

    public void setMongoServerUrl(String mongoServerUrl) {
        this.mongoServerUrl = mongoServerUrl;
    }

    public void setConnectionPerHost(String connectionPerHost) {
        this.connectionPerHost = connectionPerHost;
    }

    public void setConnectionMultiplier(String connectionMultiplier) {
        this.connectionMultiplier = connectionMultiplier;
    }

    public void setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setMaxWaitTime(String maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
    
}
