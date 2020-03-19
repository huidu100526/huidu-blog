package com.huidu.huidublog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 14:24
 * @Description: 评论实体
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 评论时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

//    @ManyToOne
//    private User user;

    /**
     * 自关联
     */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;

    /**
     * 是否管理员评论
     */
    private boolean adminComment;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
//                ", user=" + user +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}
