package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.controller.IndexController;
import com.huidu.huidublog.entity.EsBlog;
import com.huidu.huidublog.repository.EsBlogRepository;
import com.huidu.huidublog.service.EsBlogService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @auther huidu
 * @create 2020/2/27 13:47
 * @Description: es博客service实现
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {
    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private IndexController indexController;

    @Override
    public Page<EsBlog> getListBlog(Pageable pageable) {
        return esBlogRepository.findAll(pageable);
    }

    @Override
    public Page<EsBlog> getListBlog(Pageable pageable, String query) {
//        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
//        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
//        Page<EsBlog> result = esBlogRepository.findDistinctEsBlogByTitleContainingOrDescriptionContainingOrContentContaining(query, query, query, pageable);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(QueryBuilders.matchPhraseQuery("title", query));
        builder.should(QueryBuilders.matchPhraseQuery("description", query));
        builder.should(QueryBuilders.matchPhraseQuery("content", query));
        Page<EsBlog> result = (Page<EsBlog>) esBlogRepository.search(builder);
        return result;
    }
}
