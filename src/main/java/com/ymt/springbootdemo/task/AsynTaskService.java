package com.ymt.springbootdemo.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/3/5
 */
@Service
public class AsynTaskService {

    @Async
    public void f1() {
        System.out.println("f1:" + Thread.currentThread().getName() + " " + UUID.randomUUID().toString());
        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void f2() {
        System.out.println("f2:" + Thread.currentThread().getName() + " " + UUID.randomUUID().toString());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
