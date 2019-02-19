package com.ymt.springbootdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/2/19
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("ymt...");
        return "ymt...";
    }

    @RequestMapping("/index")
    public String index() {
        return "index.html";
    }
}
