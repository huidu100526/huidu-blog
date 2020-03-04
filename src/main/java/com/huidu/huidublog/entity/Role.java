package com.huidu.huidublog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/28 19:44
 * @Description: 角色
 */
@Data
@Entity
@Table(name = "role")
public class Role {
    /**
     * id
     */
    @Id
    @GeneratedValue // 自动生成
    private Long id;

    /**
     * 角色名
     */
    @NotEmpty(message = "角色名称不能为空")
    private String name;

    /**
     * 角色描述
     */
    @NotEmpty(message = "角色描述不能为空")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
