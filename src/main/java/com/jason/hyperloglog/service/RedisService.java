package com.jason.hyperloglog.service;

import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 记录用户访问
     *
     * @param user
     */
    public long statistic(String Key, String user) {
        HyperLogLogOperations<String, String> hyperLogLog = redisTemplate.opsForHyperLogLog();
        return hyperLogLog.add(Key, user);
    }

    /**
     * 统计当前UV
     *
     * @return
     */
    public long size(String Key) {
        HyperLogLogOperations<String, String> hyperLogLog = redisTemplate.opsForHyperLogLog();
        return hyperLogLog.size(Key);
    }

    /**
     * 删除当前key
     */
    public void clear(String Key) {
        HyperLogLogOperations<String, String> hyperLogLog = redisTemplate.opsForHyperLogLog();
        hyperLogLog.delete(Key);
    }

}
