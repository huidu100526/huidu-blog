package com.huidu.huidublog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 14:25
 * @Description: 用户实体
 */
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型：1、管理员    2、游客
     */
    private Integer type;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();

    public User() {
    }

    @Override
    public String
    toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
