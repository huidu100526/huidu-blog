package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @auther huidu
 * @create 2020/2/27 13:42
 * @Description: es博客service
 */
public interface EsBlogService {
    // 查询所有博客
    Page<EsBlog> getListBlog(Pageable pageable);

    // 首页搜索查询博客列表(有条件)
    Page<EsBlog> getListBlog(Pageable pageable, String query);
}
