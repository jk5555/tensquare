package com.tensquare.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.tensquare.constract.ServiceName;
import com.tensquare.user.dao.UserMapper;
import com.tensquare.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.UUID;

/**
 * @author jkun
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    public User login(User user) {
        return userMapper.selectOne(user);
    }

    @HystrixCommand(fallbackMethod = "errors")
//    @CacheResult(cacheKeyMethod = "createCacheKey")
    public String getHello(String msg) {
//        return restTemplate.getForObject("http://tensquare-article/hello/"+msg, String.class);
        throw new RuntimeException("AAA");
    }


    public String errors(String msg,Throwable e){
        System.out.println("e.getMessage() = " + e.getMessage());
        return restTemplate.getForObject("http://"+ ServiceName.TENSQUARE_ARTICLE +"/hello/"+msg, String.class);
    }


    public String createCacheKey(){
        return UUID.randomUUID().toString();
    }

}
