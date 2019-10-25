package com.ymt.springbootdemo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymt.springbootdemo.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @author yangmingtian on 2019/10/24
 */
@Controller
@RequestMapping("/haodiaoyu")
public class HaoDiaoYuController {

    private final static String logout = "https://www.haodiaoyu.com/member/logout";
    private static String loginUrl = "https://www.haodiaoyu.com/ajax/member/login?username={username}&password={password}";
    private final static String signHtmlUrl = "https://bbs.haodiaoyu.com/plugin.php?id=sign";
    private static String signUrl = "https://bbs.haodiaoyu.com/plugin.php?id=hdysign:sign&operation=qiandao&formhash={formhash}&format=empty";
    // 缓存
    private static String formhash = null;
    private static String cookie = "";
    @Autowired
    private HttpService httpService;
    @Resource(name = "sslRestTemplate")
    private RestTemplate restTemplate;

    @GetMapping("/sign")
    @ResponseBody
    public ResponseEntity<String> sign(@RequestParam String username, @RequestParam String password) {
        ResponseEntity<String> httpResponse = null;
        try {
            // 模拟登陆获取cookie
            httpResponse = httpService.httpGet(loginUrl.replace("{username}", username).replace("{password}", password), null);
            if (httpResponse.getStatusCode() == HttpStatus.OK) {
                JSONObject body = JSON.parseObject(httpResponse.getBody());
                if (body.getString("code").equals("200")) {
                    cookie = httpResponse.getHeaders().get("Set-Cookie").get(0);
                } else {
                    return httpResponse;
                }
            }
            httpResponse = httpService.httpGet(signHtmlUrl, cookie);
            if (httpResponse.getStatusCode() == HttpStatus.OK) {
                Matcher matches = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\".{8}\" />").matcher(httpResponse.getBody());
                if (matches.find()) {
                    // <input type="hidden" name="formhash" value="e08130e7" />
                    formhash = matches.group(0).split("\"")[5];
                } else {
                    matches = Pattern.compile("formhash=.{8}").matcher(httpResponse.getBody());
                    if (matches.find()) {
                        // formhash=972a0529
                        formhash = matches.group(0).split("=")[1];
                    }
                }
            }
            httpResponse = httpService.httpGet(signUrl.replace("{formhash}", formhash), cookie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return httpResponse;
    }
}
