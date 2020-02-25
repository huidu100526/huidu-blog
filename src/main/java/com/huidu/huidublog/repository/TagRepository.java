package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 20:10
 * @Description: 标签资源类
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    // 根据标签名获取标签
    Tag findByName(String name);

    // 查询使用最多的前几个标签
    @Query("select t from Tag t")
    List<Tag> findTopTags(Pageable pageable);
}
