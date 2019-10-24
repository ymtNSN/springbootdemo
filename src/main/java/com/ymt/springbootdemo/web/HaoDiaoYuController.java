package com.ymt.springbootdemo.web;

import com.ymt.springbootdemo.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @author yangmingtian on 2019/10/24
 */
@Controller
@RequestMapping("/haodiaoyu")
public class HaoDiaoYuController {

    private static String loginUrl = "https://www.haodiaoyu.com/ajax/member/login?username={username}&password={password}";
    private final static String signHtmlUrl = "https://bbs.haodiaoyu.com/plugin.php?id=sign";
    private static String signUrl = "https://bbs.haodiaoyu.com/plugin.php?id=hdysign:sign&operation=qiandao&formhash={formhash}&format=empty";

    @Autowired
    private HttpService httpService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sign")
    public ResponseEntity<String> sign(@RequestParam String username, @RequestParam String password) {
        ResponseEntity<String> responseEntity = null;
        try {
            // 模拟登陆获取cookie
            loginUrl = loginUrl.replace("{username}", username).replace("{password}", password);
            responseEntity = httpService.httpGet(loginUrl, null);
            String cookie = null;
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                cookie = responseEntity.getHeaders().get("Set-Cookie").get(0);
            }
            responseEntity = httpService.httpGet(signHtmlUrl, cookie);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Matcher matches = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\".{8}\" />").matcher(responseEntity.getBody());
                if (matches.find()) {
                    // <input type="hidden" name="formhash" value="e08130e7" />
                    signUrl = signUrl.replace("{formhash}", matches.group(0).split("\"")[5]);
                } else {
                    matches = Pattern.compile("formhash=.{8}").matcher(responseEntity.getBody());
                    if (matches.find()) {
                        // formhash=972a0529
                        signUrl = signUrl.replace("{formhash}", matches.group(0).split("=")[1]);
                    }
                }
            }
            responseEntity = httpService.httpGet(signUrl, cookie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return responseEntity;
    }
}
