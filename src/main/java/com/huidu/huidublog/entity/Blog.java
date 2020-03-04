package com.huidu.huidublog.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 14:22
 * @Description: blog实体
 */
@Data
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue // 自动生成
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 博客描述
     */
    private String description;

    /**
     * 文章内容
     */
    @Basic(fetch = FetchType.LAZY) // 要使用时才加载
    @Lob // 标记为大对象
    private String content;

    /**
     * 文章首页图
     */
    private String firstPicture;

    /**
     * 文章标记：原创、转载、翻译
     */
    private String flag;

    /**
     * 阅读次数
     */
    private Integer views;

    /**
     * 喜欢数
     */
    private Integer likes;

    /**
     * 是否开启赞赏
     */
    private boolean appreciation;

    /**
     * 是否开启转载声明
     */
    private boolean shareStatement;

    /**
     * 是否开启评论
     */
    private boolean commentabled;

    /**
     * 是否发布
     */
    private boolean published;

    /**
     * 是否推荐
     */
    private boolean recommend;

    /**
     * 发布时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private Type type;

    @ManyToOne
    private User user;

    @Transient // 这个字段不添加到数据库中，作为参数
    private String tagIds;

    public Blog() {
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    // 获取编辑博客中传的多个标签参数
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
