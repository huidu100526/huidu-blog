package com.huidu.huidublog.service;

/**
 * @auther huidu
 * @create 2020/2/28 10:35
 * @Description: 点赞记录service
 */
public interface UserLikeBlogService {
    // 根据博客id和用户id查询是否存在用户点赞记录
    boolean isLikeByBlogId(Long blogId, Long userId);

    // 保存点赞记录
    void saveUserLikeBlog(Long blogId, Long userId);

//    // 根据博客id查询是否存在与该博客相关点赞记录
//    boolean hasLikeByBlogId(Long blogId);
//
//    // 根据博客id删除点赞记录
//    void deleteBlogLikeByBolgId(Long blogId);
}
