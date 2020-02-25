package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.exception.NotFoundException;
import com.huidu.huidublog.repository.TypeRepository;
import com.huidu.huidublog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 19:22
 * @Description: 分类service实现
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> getListType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> getListType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> getListTypeTop(Integer size) {
        // 按照分类在博客中使用的篇数倒序排
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        // 排序后取出前几个(size)
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTopTypes(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
