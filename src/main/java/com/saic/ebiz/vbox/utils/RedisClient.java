package com.saic.ebiz.vbox.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.saic.ebiz.vbox.conf.CustomizedPropertyPlaceholderConfigurer;

/**
 * Redis客户端
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年11月16日
 * 
 */
public class RedisClient {
    private static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    private static ShardedJedisPool sjp = null;

    static {
        init();
    }

    /**
     * @Description: jedispool初始化
     */
    protected static synchronized boolean init() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.indexOf(CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.index"));
        shards.add(new JedisShardInfo(CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.host1"), 
                Integer.parseInt(CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.port1"))));
        shards.add(new JedisShardInfo(CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.host2"), 
                Integer.parseInt(CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.port2"))));
        logger.warn("从ucm中加载要redis1配置信息，值为："
                + CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.host1")
                + "从ucm中加载要redis2配置信息，值为："
                + CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.cxbservice.host2"));
        sjp = new ShardedJedisPool(new JedisPoolConfig(), shards);
        return true;
    }

    /**
     * @Description: 创建jedis
     * @return Jedis
     * @throws
     */
    public static ShardedJedis getRedis() {
        logger.info("RedisManage.getRedis  start");
        return sjp.getResource();
    }

    public String put(String key, String value) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.set(key, value);
            return key;
        } finally {
            sjp.returnResource(jedis);
        }
    }

    public String put(String key, Map<String, String> hash) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.hmset(key, hash);
            return key;
        } finally {
            sjp.returnResource(jedis);
        }
    }

    public String put(String key, String value, int Second) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.set(key, value);
            jedis.expire(key, Second);
            return key;
        } finally {
            sjp.returnResource(jedis);
        }
    }

    public String delete(String key) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.del(key);
            return key;
        } finally {
            sjp.returnResource(jedis);
        }
    }

    // public void flush() {
    // getJedis().flushAll();
    // }

    public void lpus(String key, String... value) {
        ShardedJedis jedis = getRedis();
        try {
            jedis.lpush(key, value);
        } finally {
            sjp.returnResource(jedis);
        }
    }

    public Map<String, String> getMap(String key) {
        ShardedJedis jedis = getRedis();
        try {
            return jedis.hgetAll(key);
        } finally {
            sjp.returnResource(jedis);
        }
    }

    public List<String> getList(String key) {
        ShardedJedis jedis = getRedis();
        try {
            return jedis.hvals(key);
        } finally {
            sjp.returnResource(jedis);
        }
    }

    public String get(String key) {
        ShardedJedis jedis = getRedis();
        try {
            return jedis.get(key);
        } finally {
            sjp.returnResource(jedis);
        }

    }

    public void setLifeCycle(String key, int time) {
        ShardedJedis jedis = getRedis();
        Long expire = jedis.expire(key, time);
        logger.warn("Redis设置key过期时间是否已经存在【expire:" + expire + "】");
    }

}
