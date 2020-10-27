package com.tensquare.article.service;

import com.tensquare.article.mongo.CommentRepository;
import com.tensquare.article.pojo.Comment;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 评论服务
 *
 * @author kun
 */
@Service
public class CommentService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;


    public Comment findById(String id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void save(Comment comment) {
        String id = idWorker.nextId() + "";
        comment.set_id(id);

        //初始化数据
        comment.setPublishdate(new Date());
        comment.setThumbup(0);

        commentRepository.save(comment);
    }

    public void update(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findByArticleId(String articleId) {
        return commentRepository.findByArticleid(articleId);
    }

    public boolean thumbup(String id, String userid) {
        //先在redis中查询是否存在点赞记录，不存在则插入，存在就不让点赞
        String key = "thumbup" + userid + "_" + id;
        String result = redisTemplate.opsForValue().get(key);
        if (result != null) {
            return false;
        }
        //创建一个条件：_id = id
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        //将点赞数增加1
        Update update = new Update();
        update.inc("thumbup");
        mongoTemplate.updateMulti(query, update, Comment.class.getSimpleName().toLowerCase());
        redisTemplate.opsForValue().set(key, key);
        return true;
    }


}
