package com.huidu.huidublog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
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
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
