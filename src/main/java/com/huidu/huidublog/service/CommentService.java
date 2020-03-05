package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.Comment;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/24 16:05
 * @Description: 评论service
 */
public interface CommentService {
    // 根据博客id查询评论列表
    List<Comment> listCommentByBlogId(Long blogId);

    // 保存评论
    void saveComment(Comment comment, Long parentCommentId);
}
