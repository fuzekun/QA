package com.fuzekun.demo1.service;


import com.fuzekun.demo1.mapper.community.AlphaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")
public class AlphaService {

    private final Logger logger = LoggerFactory.getLogger(AlphaService.class);
    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
//        System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void init() {
//        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destroy() {
//        System.out.println("销毁AlphaService");
    }

    public String find() {
        return alphaDao.select();
    }

    @Async // 在多线程的环境下被异步调用
    public void execute1() {
        logger.debug("EXE1");
    }

//    @Scheduled(initialDelay = 2000, fixedRate = 1000)
    public void execute2() {
        logger.debug("exe2");
    }

    public Object save1() {
        return new String("你好1");
    }

    public Object save2() {
        return new String("你好2");
    }
}
