package com.tensquare.user.controller;

import com.tensquare.constract.ServiceName;
import com.tensquare.user.feign.ArticleControllerApi;
import com.tensquare.user.pojo.Body;
import com.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jkun
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(ServiceName.TENSQUARE_ARTICLE)
    private ArticleControllerApi articleControllerApi;

    @GetMapping("/hello/{msg}")
    public String getHello(@PathVariable(name = "msg", required = false) String msg) {
        return userService.getHello(msg);
    }


    @GetMapping("/hello1/{msg}")
    public String getHello1(@PathVariable(name = "msg", required = false) String msg) {
        return articleControllerApi.getHello(msg);
    }

    @GetMapping("/hello2/{msg}")
    public String getHello2(@PathVariable(name = "msg", required = false) String msg) {
        return articleControllerApi.tHello1(msg) +
                articleControllerApi.tHello2(msg + msg) +
                articleControllerApi.tHello3(new Body(msg, 30));
    }

    @GetMapping("/hello3")
    public String getHello3() {
        return articleControllerApi.tHello4();
    }

}
