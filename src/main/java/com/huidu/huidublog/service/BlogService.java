package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @auther huidu
 * @create 2020/2/22 20:32
 * @Description: 博客service
 */
public interface BlogService {
    // 根据id查询博客
    Blog getBlog(Long id);

    // 根据id查询博客(markdown转换为html)
    Blog getBlobAndConvert(Long id);

    // 后台管理分页查询博客列表(有条件)
    Page<Blog> getListBlog(Pageable pageable, BlogQuery blog);

    // 首页搜索查询博客列表(有条件)
    Page<Blog> getListBlog(Pageable pageable, String query);

    // 按分类条件进行分页查询
    Page<Blog> getListBlog(Pageable pageable, Long tagId);

    // 分页查询博客列表(true查询首页发布博客，false查询所有列表)
    Page<Blog> getListBlog(Pageable pageable, boolean flag);

    // 新增博客
    Blog saveBlog(Blog blog);

    // 根据id和博客修改博客
    Blog updateBlog(Long id, Blog blog);

    // 删除博客
    void deleteBlog(Long id);

    // 查询最新推荐的前几篇博客
    List<Blog> listRecommendBlogTop(Integer size);

    // 归档按年份查询博客列表
    Map<String, List<Blog>> archiveBlog();

    // 统计所有博客数量
    Long countBlog();

    // 查询最新发布的三篇博客
    List<Blog> listNewBlogTop(Integer size);

    // 根据博客id添加喜欢数
    Integer addLikeByBlogId(Long blogId);
}
