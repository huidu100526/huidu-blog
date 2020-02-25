package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 19:06
 * @Description: 分类资源类
 */
public interface TypeRepository extends JpaRepository<Type, Long> {
    // 根据分类名查询分类
    Type findByName(String name);

    // 查询使用最多的前几个分类
    @Query("select t from Type t")
    List<Type> findTopTypes(Pageable pageable);
}
