package com.tensquare.user.feign;

import com.tensquare.constract.ServiceName;
import com.tensquare.user.pojo.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ArticleControllerFallBack implements ArticleControllerApi{

    @Autowired
    @Qualifier(ServiceName.TENSQUARE_ARTICLE)
    private ArticleControllerApi articleControllerApi;

    @Override
    public String getHello(@PathVariable(name = "msg", required = false) String msg) {
        return "null";
    }

    @Override
    public String tHello1(@RequestParam("name") String name) {
        return "null";
    }

    @Override
    public String tHello2(@RequestHeader("header") String header) {
        return "null";
    }

    @Override
    public String tHello3(@RequestBody Body body) {
        return "null";
    }

    @Override
    public String tHello4() {
        System.out.println(" 我又进来了。。。。。 "+ System.currentTimeMillis());
        return articleControllerApi.tHello4();
    }
}
