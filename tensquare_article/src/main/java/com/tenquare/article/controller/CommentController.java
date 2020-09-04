package com.tenquare.article.controller;

import com.tenquare.article.pojo.Comment;
import com.tenquare.article.service.CommentService;
import com.tenquare.entity.Result;
import com.tenquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论接口
 *
 * @author kun
 */
@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 根据id查询评论
     */
    @RequestMapping(value = "/c/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        Comment comment = commentService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", comment);
    }

    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Comment> list = commentService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment) {
        commentService.save(comment);
        return new Result(true, StatusCode.OK, "新增成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable String id,
                         @RequestBody Comment comment) {
        comment.set_id(id);
        commentService.update(comment);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据文章id查询评论
     */
    @RequestMapping(value = "/a/{articleId}", method = RequestMethod.GET)
    public Result findCommentByArticleId(@PathVariable String articleId) {
        List<Comment> list = commentService.findByArticleId(articleId);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 给评论点赞(不能重复点赞)
     */
    @RequestMapping(value = "/like/{id}/{userid}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id, @PathVariable String userid) {
        boolean b = commentService.thumbup(id, userid);
        if (b) {
            return new Result(b, StatusCode.OK, "点赞成功");
        } else {
            return new Result(b, StatusCode.REPERROR, "不能重复点赞");
        }
    }
}
