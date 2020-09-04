package com.tenquare;
import java.util.Date;

import com.tenquare.article.ArticleApplication;
import com.tenquare.article.mongo.CommentRepository;
import com.tenquare.article.pojo.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * mongoDB测试
 *
 * @author kun
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class MongoDBTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void test() throws InterruptedException {
        Comment comment = new Comment();
        comment.set_id("aaa");
        comment.setArticleid("1");
        comment.setContent("aaaaaaaaaa");
        comment.setUserid("1024");
        comment.setParentid("aa");
        comment.setPublishdate(new Date());
        comment.setThumbup(0);

        Comment save = commentRepository.save(comment);
        System.out.println(save);
    }



}
