package szy.alibaba.cloud.mongo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szy.alibaba.cloud.mongo.module.Article;
import szy.alibaba.cloud.mongo.service.ArticleServiceImpl;

import java.util.List;

@RestController
/* https://blog.csdn.net/qq_50313418/article/details/127092264 */
public class ArticleController {

    @Autowired
    ArticleServiceImpl articleService;


    @RequestMapping(value = "/art/insert", method = RequestMethod.POST)
    public int insertArticle(@RequestBody Article article){
        return articleService.create(article);
    }


    @RequestMapping(value = "/art/getList", method = RequestMethod.GET)
    public List<Article> getList(@RequestBody Article article){
        return articleService.list(article);
    }
}
