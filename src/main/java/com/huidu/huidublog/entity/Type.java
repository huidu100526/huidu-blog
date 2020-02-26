package com.huidu.huidublog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 14:25
 * @Description: 分类实体
 */
@Data
@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 分类名
     */
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
