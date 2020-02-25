package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 20:07
 * @Description: 标签service
 */
public interface TagService {
    // 新增标签
    Tag saveTag(Tag type);

    // 根据id获取标签
    Tag getTag(Long id);

    // 根据标签名获取标签
    Tag getTagByName(String name);

    // 分页查询标签列表
    Page<Tag> getListTag(Pageable pageable);

    // 获取所有标签
    List<Tag> getListTag();

    // 获取使用最多的前几个标签
    List<Tag> getListTagTop(Integer size);

    // 根据多个标签号查询标签
    List<Tag> getListTag(String ids);

    // 根据id和标签修改标签
    Tag updateTag(Long id, Tag type);

    // 删除标签
    void deleteTag(Long id);
}
