package com.huidu.huidublog.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @auther huidu
 * @create 2020/2/27 13:13
 * @Description: elasticsearch文档类
 */
@Data
@Document(indexName = "huidu_blog", type = "doc")
public class EsBlog {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String firstPicture;

    @Field(type = FieldType.Text)
    private String flag;

    @Field(type = FieldType.Long)
    private Integer views;

    @Field(type = FieldType.Long)
    private Integer likes;

    @Field(type = FieldType.Boolean)
    private boolean appreciation;

    @Field(type = FieldType.Boolean)
    private boolean shareStatement;

    private boolean commentabled;

    @Field(type = FieldType.Boolean)
    private boolean published;

    @Field(type = FieldType.Boolean)
    private boolean recommend;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Long)
    private Long typeId;
}
