package com.huidu.huidublog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 14:24
 * @Description: 标签实体
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 标签名
     */
    @NotEmpty(message = "标签名称不能为空")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
