package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 20:37
 * @Description: 博客资源类
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    // 查询最新推荐的前几篇博客
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findBlogTop(Pageable pageable);

    // 按搜索条件查询博客列表(从标题、描述、内容中搜索)
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    // 根据id修改博客中的访问量，每次累加1
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    void updateViews(Long id);

    // 根据年份分组查询博客列表
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b" +
            ".updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    // 查询某一年发布的所有博客
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);

    // 查询最新发布的博客
    @Query("select b from Blog b")
    List<Blog> findNewBlogTop(Pageable pageable);

    // 根据id修改博客中的喜欢数，点赞
    @Transactional
    @Modifying
    @Query("update Blog b set b.likes = b.likes + 1 where b.id = ?1")
    void updateLikes(Long id);
}
