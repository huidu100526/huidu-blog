package com.huidu.huidublog.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @auther huidu
 * @create 2020/2/28 9:58
 * @Description: 文章点赞记录
 */
@Data
@Entity
@Table(name = "user_like_blog")
public class UserLikeBlog {
    @Id
    @GeneratedValue // 自动生成
    private Long id;

    /**
     * 点赞人id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 被点赞博客id
     */
    @Column(name = "blog_id")
    private Long blogId;
}
