package com.tenquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tenquare.article.dao.ArticleMapper;
import com.tenquare.article.pojo.Article;
import com.tenquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章业务层
 *
 * @author kun
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据id查找文章
     *
     * @param id 文章id
     * @return com.tenquare.article.pojo.Article
     * @author kun
     */
    public Article findById(String id) {
        return articleMapper.selectById(id);
    }

    /**
     * 查找所有文章
     *
     * @return java.util.List<com.tenquare.article.pojo.Article>
     * @author kun
     */
    public List<Article> findList() {
        return articleMapper.selectList(null);
    }

    public void add(Article article) {
        long id = idWorker.nextId();
        article.setId(id + "");
        articleMapper.insert(article);
    }

    public void deleteById(String id) {
        articleMapper.deleteById(id);
    }

    public void update(Article article) {
        Wrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("id", article.getId());
        articleMapper.update(article, wrapper);
    }


    /**
     * 查找文章评论数满足条件的数据(条件查询)
     *
     * @param article 文章对象
     * @author kun
     */
    public List<Article> findByComment(Article article) {
        Wrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.gt(article.getComment() != null, "comment", article.getComment());
        return articleMapper.selectList(wrapper);
    }


    /**
     * 分页条件查询
     *
     * @param map 文章对象
     * @return java.util.List<com.tenquare.article.pojo.Article>
     * @author kun
     */
    public Page<Article> findByPage(Map<String, Object> map ,int page, int size) {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        keySet.forEach(key -> wrapper.eq(null != map.get(key), key, map.get(key)));
        Page<Article> articlePage = new Page<>(page,size);
        List<Article> articles = articleMapper.selectPage(articlePage, wrapper);
        return articlePage.setRecords(articles);
    }


}
