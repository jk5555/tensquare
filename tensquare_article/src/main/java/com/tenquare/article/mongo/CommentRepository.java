package com.tenquare.article.mongo;

import com.tenquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 评论
 *
 * @author kun
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByArticleid(String articleId);
}
