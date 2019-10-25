package com.ymt.springbootdemo.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by @author yangmingtian on 2019/10/24
 */
@Service
public class HttpService {

    @Resource(name = "sslRestTemplate")
    private RestTemplate restTemplate;

    public ResponseEntity<String> httpGet(String url, String cookie) {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Host", "bbs.haodiaoyu.com");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.add("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Cache-Control", "max-age=0");
        headers.add("Connection", "keep-alive");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
//        System.out.println(resp.getBody());
//        System.out.println("-------------------------------------------------------------------------------------");
        return resp;
    }
}
