package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/24 16:05
 * @Description: 评论资源类
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 根据博客id和没有子评论的情况按评论时间倒序的查询评论列表
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

//    // 根据博客id查询是否存在与该博客相关的评论记录
//    @Query("select b from Comment b where b.blog")
//    List<Comment> findCommentsByBlogId();
}
