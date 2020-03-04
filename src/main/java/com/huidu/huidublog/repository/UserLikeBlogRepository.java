package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.UserLikeBlog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther huidu
 * @create 2020/2/28 10:31
 * @Description: 博客点赞记录资源类
 */
public interface UserLikeBlogRepository extends JpaRepository<UserLikeBlog, Long> {
    // 根据博客id和用户id查询点赞记录
    UserLikeBlog findUserLikeBlogByBlogIdAndUserId(Long blogId, Long userId);

    // 根据博客id删除点赞记录
    void deleteUserLikeBlogByBlogId(Long blogId);
}
