package com.tensquare.article.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import util.IdWorker;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, int page, int size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    //spring data redis提供的模板对象 通过这个模块就可以对redis操作缓存
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据ID查询实体
     * //文章缓存到redis
     *
     * @param id
     * @return
     */
    public Article findById(String id) {

        //redisTemplate.opsForValue().get("key");//查询
        //redisTemplate.delete("key");//删除
        //redisTemplate.opsForValue().set("key","value");

        //首先到redis根据文章id查询缓存文章
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);

        //缓存文章为空
        if (article == null) {
            System.out.println("文章缓存为空，从数据库中查询。。。。");
            //从数据库中查询返回文章数据
            article = articleDao.findById(id).get();
            //将文章数据再存入redis
           // redisTemplate.opsForValue().set("article_" + id, article);

            //缓存过期处理
            redisTemplate.opsForValue().set("article_" + id, article,1000, TimeUnit.SECONDS);
        }
        return article;
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *修改或删除后清除缓存
     * @param article
     */
    public void update(Article article) {
        articleDao.save(article);
        redisTemplate.delete("article_"+article.getId());
    }

    /**
     * 删除
     *修改或删除后清除缓存
     * @param id
     */
    public void deleteById(String id) {
        articleDao.deleteById(id);
        redisTemplate.delete("article_"+id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {

        return new Specification<Article>() {

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 专栏ID
                if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid"))) {
                    predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + (String) searchMap.get("columnid") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 文章正文
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 文章封面
                if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
                }
                // 是否公开
                if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic"))) {
                    predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + (String) searchMap.get("ispublic") + "%"));
                }
                // 是否置顶
                if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop"))) {
                    predicateList.add(cb.like(root.get("istop").as(String.class), "%" + (String) searchMap.get("istop") + "%"));
                }
                // 审核状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                // 所属频道
                if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid"))) {
                    predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + (String) searchMap.get("channelid") + "%"));
                }
                // URL
                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }
                // 类型
                if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /**
     * 文章审核  更新文章表中状态/examine/{articleId}
     */
    //@Transactional
    public void examine(String articleId) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus) {
                //自定义jqpl语句 更新操作
                articleDao.examine(articleId);
                int i=1/0;
                return null;
            }
        });
    }

    /**
     * 文章点赞 更新文章表中点赞数
     * /thumbup/{articleId}
     */
    @Transactional
    public void thumbup(String articleId) {
        articleDao.thumbup(articleId);
    }
}
