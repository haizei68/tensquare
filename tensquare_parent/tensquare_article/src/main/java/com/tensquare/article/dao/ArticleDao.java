package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    /**
     * 文章审核  更新文章表中状态/examine/{articleId}
     */
    @Query("update Article set state = '1' where id = ?1")
    @Modifying //一定要加 告诉spring data jpa当前是非查询操作
    void examine(String articleId);

    /**
     * 文章点赞 更新文章表中点赞数
     * /thumbup/{articleId}
     */
    @Query("update Article set thumbup = thumbup+1 where id = ?1")
    @Modifying //一定要加
    void thumbup(String articleId);
}
