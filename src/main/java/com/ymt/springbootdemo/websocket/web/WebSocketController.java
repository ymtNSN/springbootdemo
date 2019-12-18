package com.ymt.springbootdemo.websocket.web;

import com.ymt.springbootdemo.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yangmingtian on 2019/12/18
 */
@RestController
@RequestMapping("/api/ws")
public class WebSocketController {

    @RequestMapping(value = "/sendAll", method = RequestMethod.GET)
    public String sendAllMessage(@RequestParam(required = true) String message) {
        try {
            WebSocketServer.broadCastInfo(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    /**
     * 指定会话ID发消息
     *
     * @param message 消息内容
     * @param id      连接会话ID
     * @return
     */
    @RequestMapping(value = "/sendOne", method = RequestMethod.GET)
    public String sendOneMessage(@RequestParam(required = true) String message, @RequestParam(required = true) String id) {
        try {
            WebSocketServer.sendMessage(message, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
