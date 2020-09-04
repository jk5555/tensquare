package com.tenquare.article;

import com.tenquare.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 文章微服务启动类
 *
 * @author kun
 */
@SpringBootApplication
@MapperScan("com.tenquare.article.dao")
@ComponentScan("com.tenquare")
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    /**
     * 创建主键策略对象，并注册到spring容器中

     * @return com.tenquare.util.IdWorker
     * @author kun
     */
    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(1, 1);
    }
}
