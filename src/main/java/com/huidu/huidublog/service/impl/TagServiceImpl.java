package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.Tag;
import com.huidu.huidublog.exception.NotFoundException;
import com.huidu.huidublog.repository.TagRepository;
import com.huidu.huidublog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 20:08
 * @Description: 标签service实现
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> getListTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> getListTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getListTagTop(Integer size) {
        // 按照标签在博客中使用的篇数倒序排
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        // 排序后取出前几个(size)
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTopTags(pageable);
    }

    @Override
    public List<Tag> getListTag(String ids) {
        // 将多个id转为列表
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (String anIdarray : idarray) {
                list.add(new Long(anIdarray));
            }
        }
        return tagRepository.findAllById(list);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
