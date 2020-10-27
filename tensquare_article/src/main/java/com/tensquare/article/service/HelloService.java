package com.tensquare.article.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class HelloService {


    public String getString(String msg){
        return StringUtils.isEmpty(msg)? "defaultMsg" : "hello-"+msg;
    }
}
