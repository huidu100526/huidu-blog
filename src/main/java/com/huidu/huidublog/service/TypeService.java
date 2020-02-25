package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 19:06
 * @Description: 分类service
 */
public interface TypeService {
    // 新增分类
    Type saveType(Type type);

    // 根据id获取分类
    Type getType(Long id);

    // 根据分类名获取分类
    Type getTypeByName(String name);

    // 分页查询分类
    Page<Type> getListType(Pageable pageable);

    // 查询所有分类
    List<Type> getListType();

    // 查询最热门分类列表(可定义查询前多少)
    List<Type> getListTypeTop(Integer size);

    // 根据id和分类修改分类
    Type updateType(Long id, Type type);

    // 删除分类
    void deleteType(Long id);
}
