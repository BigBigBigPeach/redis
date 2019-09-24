package com.jason.hyperloglog.web;

import com.jason.hyperloglog.constant.RedisConstant;
import com.jason.hyperloglog.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RequestMapping("/visit")
@RestController
@Slf4j
public class WebController implements InitializingBean {

    @Resource
    private RedisService redisService;

    @GetMapping("/{user}")
    public void visit(@PathVariable("user") String user) {
        redisService.statistic(RedisConstant.KEY_USER_STAT, user);
    }

    @Scheduled(fixedRate = 2000)
    public void getSize() {
        log.warn("定时任务扫描hyperLogLog的数量:{}", redisService.size(RedisConstant.KEY_USER_STAT));
    }


    @Override
    public void afterPropertiesSet() {
        redisService.clear(RedisConstant.KEY_USER_STAT);
    }
}
