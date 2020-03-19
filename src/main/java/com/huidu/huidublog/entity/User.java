package com.huidu.huidublog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huidu.huidublog.enums.UserTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@NoArgsConstructor
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
     * 用户类型
     */
    private Integer type = UserTypeEnum.USER.getCode();

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

    /**
     * 角色
     */
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
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
                ", roles=" + roles +
                ", blogs=" + blogs +
//                ", comments=" + comments +
                '}';
    }
}
