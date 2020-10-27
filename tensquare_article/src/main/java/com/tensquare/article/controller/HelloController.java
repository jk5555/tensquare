package com.tensquare.article.controller;

import com.tensquare.article.pojo.Body;
import com.tensquare.article.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    int i = 0;

    @GetMapping("/hello/{msg}")
    public String getHello(@PathVariable(name = "msg", required = false) String msg, HttpServletRequest request) throws Exception {
        i++;
        if (i%2 == 0) {
            throw new Exception("aa");
        }
        int i = new Random().nextInt(3000);
        Thread.sleep(i);
        return request.getRemoteAddr()+"-->"+request.getLocalAddr()+":"+request.getLocalPort()+"----"+ helloService.getString(msg);
    }

    @GetMapping("/hello1")
    public String getHello1(@RequestParam String name) {
        return "hello " + name;
    }

    @GetMapping("/hello2")
    public String getHello2(@RequestHeader String header) {
        return "hello " + header;
    }

    @PostMapping("/hello3")
    public String getHello3(@RequestBody Body body) {
        return "hello " + body;
    }

    @GetMapping("/hello4")
    public String getHello4() {
        return "hello ";
    }

}
