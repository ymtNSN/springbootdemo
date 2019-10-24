package com.ymt.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @author yangmingtian on 2019/10/24
 */
@Service
public class HttpService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> httpGet(String url, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        System.out.println(resp.getBody());
        System.out.println("-------------------------------------------------------------------------------------");
        return resp;
    }

    //
    //    @Test
    public String test2() {
        try {
            String url = "https://www.haodiaoyu.com/ajax/member/login?username=zyjzyj&password=107077";
            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "text/html");
//            headers.add(
//                    "Accept",
//                    "text/html,application/xhtml+xml,application/xml,application/json;q=0.9,image/webp,*/*;q=0.8");
////            headers.add("Accept-Encoding", "gzip,deflate,sdch");
//            headers.add("Cache-Control", "max-age=0");
            headers.add("Connection", "keep-alive");
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                    HttpMethod.GET, requestEntity, String.class);
            HttpHeaders headers1 = responseEntity.getHeaders();
            return headers1.get("Set-Cookie").get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //    @Test
    public Map<String, String> test1() {
        try {
            String url = "https://bbs.haodiaoyu.com/plugin.php?id=sign";
            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "text/html");
//            headers.add(
//                    "Accept",
//                    "text/html,application/xhtml+xml,application/xml,application/json;q=0.9,image/webp,*/*;q=0.8");
////            headers.add("Accept-Encoding", "gzip,deflate,sdch");
//            headers.add("Cache-Control", "max-age=0");
            headers.add("Connection", "keep-alive");
            Map<String, String> map = new HashMap<>();
            List<String> cookies = new ArrayList<String>();
//            /* 登录获取Cookie 这里是直接给Cookie，可使用下方的login方法拿到Cookie给入*/
            String cookie = test2();
            map.put("cookie", cookie);
            cookies.add(cookie);       //在 header 中存入cookies
            headers.put(HttpHeaders.COOKIE, cookies);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                    HttpMethod.GET, requestEntity, String.class);
            String body = responseEntity.getBody();
            System.out.println(body);
            System.out.println("------------------------------------------");
            Matcher matches = Pattern.compile("formhash=.{8}").matcher(body);
            if (matches.find()) {
                map.put("formhash", matches.group(0));
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //    @Test
    public String test3() {
        try {
            String url = "https://bbs.haodiaoyu.com/plugin.php?id=hdysign:sign&operation=qiandao&formhash=f1f7f01e&format=empty";
            HttpHeaders headers = new HttpHeaders();
//            headers.add("Host", "bbs.haodiaoyu.com");
//            headers.add(
//                    "Accept",
//                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            headers.add("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
////            headers.add("Accept-Encoding", "gzip, deflate, br");
//            headers.add("Cache-Control", "max-age=0");
            headers.add("Connection", "keep-alive");
////            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
//            headers.add("Upgrade-Insecure-Requests", "1");
////            headers.add("Cookie", "KKOM_b949_saltkey=B66lgmoL; KKOM_b949_lastvisit=1571713636; KKOM_b949_ulastactivity=1571884612%7C0; KKOM_b949_lastact=1571884644%09plugin.php%09; KKOM_b949_connect_is_bind=0; KKOM_b949_nofavfid=1; Hm_lvt_33b44eaa652bf7aa2fca0c2031abe3ba=1571717234,1571819211,1571884613; CITY_CODE=310100; CITY_SHORT_NAME=%E4%B8%8A%E6%B5%B7; UM_distinctid=16df23b5dc7dd-009d64a224795c8-4c312373-1fa400-16df23b5dc836a; CNZZDATA2006713=cnzz_eid%3D1350692298-1571727043-%26ntime%3D1571727043; KKOM_b949_myrepeat_rr=R0; KKOM_b949_CITYPINYIN=shanghai; CITYPINYIN=shanghai; KKOM_b949_noticeTitle=1; Hm_lpvt_33b44eaa652bf7aa2fca0c2031abe3ba=1571884644; authkey=33c5iUZT1XyQFR4g1vRiW4p5keI%2Fk%2FKmE8oS3r6iOD3I5iB4JUTbk%2Bx2QbrOAdMXJrDb6OBHXjVGyDa0h0iVIeBjxMIf7hFiAzFCQQJYmg; KKOM_b949_onlineusernum=7910");
            List<String> cookies = new ArrayList<String>();
////            /* 登录获取Cookie 这里是直接给Cookie，可使用下方的login方法拿到Cookie给入*/
            Map<String, String> map = test1();
            cookies.add(map.get("cookie"));       //在 header 中存入cookies
            headers.put(HttpHeaders.COOKIE, cookies);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            url = url.replace("formhash=f1f7f01e", map.get("formhash"));
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            String body = responseEntity.getBody();
            System.out.println(body);
            return body;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
