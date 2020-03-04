package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @auther huidu
 * @create 2020/2/27 13:36
 * @Description: es文档资源类
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Long> {
    // 全局分页检索，从标题、描述、内容中检索
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrDescriptionContainingOrContentContaining(String title, String description, String content, Pageable pageable);
}
