package com.ymt.springbootdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by @author yangmingtian on 2019/11/25
 */
public class SpringTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:test.xml");
        Object ymt = context.getBean("people");
        System.out.println(ymt);
    }
}
