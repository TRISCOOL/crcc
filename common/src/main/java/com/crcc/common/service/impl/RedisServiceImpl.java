package com.crcc.common.service.impl;

import com.crcc.common.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private JedisPool pool;


    @Value("${spring.redis.database}")
    private int database;

    public void setStr(String key, String val, final long timeout) {
        try(Jedis jedis = pool.getResource()) {
            jedis.select(database);
            jedis.set(key, val);
            if (timeout == 0l) {
                setStr(key, val);
            } else {
                jedis.expire(key, (int)timeout);
            }

        }

    }

    public Map<String,String> hgetAll(String key){
        try(Jedis jedis = pool.getResource()){
            jedis.select(database);
            return jedis.hgetAll(key);
        }
    }


    public void setStr(String key, String val) {
        try(Jedis jedis = pool.getResource()) {
            jedis.select(database);
            jedis.set(key, val);
        }
    }

    public String getStr(String key) {
        try(Jedis jedis = pool.getResource()) {
            jedis.select(database);
            return jedis.get(key);
        }
    }

    public boolean exists(String key) {
        try(Jedis jedis = pool.getResource()) {
            jedis.select(database);
            return jedis.exists(key);
        }
    }

    @Override
    public void delStr(String key) {
        try (Jedis jedis = pool.getResource()){
            jedis.select(database);
            jedis.del(key);
        }
    }

    @Override
    public Long incrby(String key, Integer add) {
        try (Jedis jedis = pool.getResource()){
            jedis.select(database);
            return jedis.incrBy(key,add);
        }
    }

    @Override
    public void setExpire(String key, Long timeout) {
        try (Jedis jedis = pool.getResource()){
            jedis.select(database);
            jedis.expire(key,Integer.parseInt(timeout+""));
        }
    }
}
