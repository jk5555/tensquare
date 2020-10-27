package com.tensquare.user.feign;


import com.tensquare.constract.ServiceName;
import com.tensquare.user.config.DisableHystrixConfiguration;
import com.tensquare.user.pojo.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 指定feign的客户端（该配置指定的客户端禁用了（未配置）hystrix断路器）
 * @author jkun
 */
@FeignClient(name = ServiceName.TENSQUARE_ARTICLE,qualifier = ServiceName.TENSQUARE_ARTICLE, fallback = ArticleControllerFallBack.class)
public interface ArticleControllerApi {

    @GetMapping("/hello/{msg}")
    String getHello(@PathVariable(name = "msg", required = false) String msg);

    @GetMapping("/hello1")
    String tHello1(@RequestParam("name") String name);

    @GetMapping("/hello2")
    String tHello2(@RequestHeader("header") String header);

    @PostMapping("/hello3")
    String tHello3(@RequestBody Body body);

    @GetMapping("/hello4")
    String tHello4();
}
