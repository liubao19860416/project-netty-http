package com.saic.ebiz.vbox.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Redis客户端
 * 
 * @author Liubao
 * @version redis.version==>2.7.2
 * @Date 2016年04月15日
 * 
 */
public class RedisClient {
    private static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    //初始化常量信息,dbIndex1从0开始
    private static final int dbIndex1=3;
//    private static final String host1="192.168.2.182";
    private static final String host1="127.0.0.1";
    private static final int port1=6379;
    private static final String auth1="jh123";
//    private static final String host2="192.168.2.192";
    private static final String host2="127.0.0.1";
    private static final int port2=6379;
    private static final int dbIndex2=2;
    private static final String auth2="jh123";
    //格式
//    private static final String uri1="redis://:jh123@192.168.2.182:6379/0";
//    private static final String uri2="redis://:jh123@192.168.2.192:6379/2";
    private static final String uri1;
    private static final String uri2;
    
    private static ShardedJedisPool sjp = null;
    
    /**
     * 测试dbIndex方法
     */
    @Test
    public void testName0() throws Exception {
        String value = get("loginKey_5");
        System.out.println(value);
        value = get("loginKey_10");
        System.out.println(value);
    }
    
    @Test
    public void testName1() throws Exception {
        String key="userName";
        String value="LiuBao";
        String result = put(key, value);
        System.out.println(result);
        key="userName1";
        value="LiuBao1";
        result = put(key, value);
        System.out.println(result);
        value = get(key);
        System.out.println(value);
    }
    
    @Test
    public void testName2() throws Exception {
        String key="userName";
        String value = get(key);
        System.out.println(value);
    }
    
    
    static {
        uri1="redis://:"+auth1+"@"+host1+":"+port1+"/"+dbIndex1;
        uri2="redis://:"+auth2+"@"+host2+":"+port2+"/"+dbIndex2;
        logger.warn("初始化uri信息为:uri1={}",uri1);
        logger.warn("初始化uri信息为:uri2={}",uri2);
        Assert.assertNotNull(uri1);
        assertNotNull(uri2);
        init();
    }

    /**
     * @Description: jedispool初始化
     * 保存到db=2中去了,最后一个添加的机器ip有效,且使用了其对应的dbIndex.
     */
    protected static synchronized boolean init() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        /**
         * 必须使用如下方式初始化,才能构建对应的dbIndex信息
         */
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(uri1);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(uri2);
        shards.add(jedisShardInfo1);
        shards.add(jedisShardInfo2);
        sjp = new ShardedJedisPool(new JedisPoolConfig(), shards);
        return true;
    }
    
    @Deprecated
    protected static synchronized boolean init0() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        //shards.indexOf(dbIndex);
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(host1, port1);
        jedisShardInfo1.setPassword(auth1);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(host2, port2);
        jedisShardInfo2.setPassword(auth2);
        shards.add(jedisShardInfo1);
        shards.add(jedisShardInfo2);
        sjp = new ShardedJedisPool(new JedisPoolConfig(), shards);
        return true;
    }

    /**
     * @Description: 创建jedis
     * @return Jedis
     * @throws
     */
    private static ShardedJedis getRedis() {
        logger.info("RedisManage.getRedis  start");
        return sjp.getResource();
    }

    public static String put(String key, String value) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.set(key, value);
            return key;
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    public static String put(String key, Map<String, String> hash) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.hmset(key, hash);
            return key;
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    public static String put(String key, String value, int Second) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.set(key, value);
            jedis.expire(key, Second);
            return key;
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    public static String delete(String key) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.del(key);
            return key;
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    // public void flush() {
    // getJedis().flushAll();
    // }

    public static void lpus(String key, String... value) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.lpush(key, value);
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    public static Map<String, String> getMap(String key) {
        ShardedJedis jedis = getRedis();
        try {
            return jedis.hgetAll(key);
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    public static List<String> getList(String key) {
        ShardedJedis jedis = getRedis();
        try {
            return jedis.hvals(key);
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }
    }

    public static String get(String key) {
        ShardedJedis jedis = getRedis();
        try {
            return jedis.get(key);
        } finally {
            //sjp.returnResource(jedis);
            sjp.returnResourceObject(jedis);
        }

    }

    public static void setLifeCycle(String key, int time) {
        ShardedJedis jedis = getRedis();
        Long expire = jedis.expire(key, time);
        logger.warn("Redis设置key过期时间是否已经存在【expire:" + expire + "】");
    }

}
