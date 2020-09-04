package com.tenquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tenquare.article.pojo.Article;
import com.tenquare.article.service.ArticleService;
import com.tenquare.entity.PageResult;
import com.tenquare.entity.Result;
import com.tenquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文章控制器
 *
 * @author kun
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping
    public Result findAll() {
        List<Article> list = articleService.findList();
        return new Result(true, StatusCode.OK, "success", list);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable("id") String id) {
        Article article = articleService.findById(id);
        return new Result(true, StatusCode.OK, "success", article);
    }

    @PostMapping("/add")
    public Result addOne(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "success");
    }

    @DeleteMapping("/{id}")
    public Result deleteOne(@PathVariable("id") String id) {
        articleService.deleteById(id);
        return new Result(true, StatusCode.OK, "success");
    }

    @PutMapping("/{id}")
    public Result updateOne(@PathVariable("id") String id, @RequestBody Article article) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "success");
    }

    @PostMapping("/search/{page}/{size}")
    public Result findByPage(@RequestBody Map<String, Object> map, @PathVariable int page, @PathVariable int size) {
        Page<Article> page1 = articleService.findByPage(map, page, size);
        PageResult<Article> pageResult = new PageResult<Article>();
        pageResult.setRows(page1.getRecords());
        pageResult.setTotal(page1.getTotal());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @RequestMapping(value="/exception", method = RequestMethod.GET)
    public Result exception() throws Exception {
        throw new Exception("测试统一异常处理");
    }


}
