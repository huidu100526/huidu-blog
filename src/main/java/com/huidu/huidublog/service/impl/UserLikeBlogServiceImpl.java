package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.UserLikeBlog;
import com.huidu.huidublog.repository.UserLikeBlogRepository;
import com.huidu.huidublog.service.UserLikeBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther huidu
 * @create 2020/2/28 10:41
 * @Description: 点赞记录service实现
 */
@Service
public class UserLikeBlogServiceImpl implements UserLikeBlogService {
    @Autowired
    private UserLikeBlogRepository userLikeBlogRepository;

    @Override
    public boolean isLikeByBlogId(Long blogId, Long userId) {
        UserLikeBlog userLikeBlog = userLikeBlogRepository.findUserLikeBlogByBlogIdAndUserId(blogId, userId);
        // 如果为空则不存在
        return userLikeBlog != null;
    }

    @Override
    public void saveUserLikeBlog(Long blogId, Long userId) {
        UserLikeBlog userLikeBlog = new UserLikeBlog();
        userLikeBlog.setBlogId(blogId);
        userLikeBlog.setUserId(userId);
        // 保存点赞记录
        userLikeBlogRepository.save(userLikeBlog);
    }

    @Override
    public void deleteBlogLikeByBolgId(Long blogId) {
        userLikeBlogRepository.deleteUserLikeBlogByBlogId(blogId);
    }
}
