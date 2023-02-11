package szy.alibaba.cloud.mongo.service;

import szy.alibaba.cloud.mongo.module.Article;

import java.util.List;

public interface ArticleService {

    /**
     * 添加文章
     * @param article
     * @return
     */
    int create(Article article);

    /**
     * 删除文章
     */
    int delete(List<String> ids);


    List<Article> list(Article article);
}
