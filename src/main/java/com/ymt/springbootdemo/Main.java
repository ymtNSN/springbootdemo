package com.ymt.springbootdemo;

import com.ymt.springbootdemo.task.AsynTaskService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/3/5
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringbootdemoApplication.class);
        AsynTaskService service = context.getBean(AsynTaskService.class);
        for (int i = 0; i < 10; i++) {
            service.f1();
            service.f2();
        }
        context.close();
    }
}
