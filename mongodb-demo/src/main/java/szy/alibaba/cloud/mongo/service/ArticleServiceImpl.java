package szy.alibaba.cloud.mongo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import szy.alibaba.cloud.mongo.module.Article;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @Override
    public int create(Article article) {
        Article save = mongoTemplate.save(article);
        return 1;
    }

    /**
     * 删除文章
     *
     * @param ids
     */
    @Override
    public int delete(List<String> ids){
        List<Article> deleteList = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        mongoTemplate.remove(query, Article.class);
        return 1;
    }

    @Override
    public List<Article> list(Article article) {
        Query query = new Query();
        if (article.getId() != null){
            query.addCriteria(Criteria.where("id").is(article.getId()));
        }
        if (article.getArticleName() != null){
            query.addCriteria(Criteria.where("articleName").is(article.getArticleName()));
        }
        if (article.getContent() != null){
            query.addCriteria(Criteria.where("content").is(article.getContent()));
        }

        List<Article> list = mongoTemplate.find(query, Article.class);
        return list;
    }

}
