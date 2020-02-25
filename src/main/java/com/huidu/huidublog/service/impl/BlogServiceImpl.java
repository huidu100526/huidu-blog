package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.VO.BlogQuery;
import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.exception.NotFoundException;
import com.huidu.huidublog.repository.BlogRepository;
import com.huidu.huidublog.service.BlogService;
import com.huidu.huidublog.utils.MarkdownUtils;
import com.huidu.huidublog.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @auther huidu
 * @create 2020/2/22 20:36
 * @Description: 博客service实现
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Blog getBlobAndConvert(Long id) {
        // 获取blog
        Blog blog = blogRepository.getOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        // 新创建，防止重新设置content进数据库
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        // 进行markdown转html
        String htmlContent = MarkdownUtils.markdownToHtmlExtensions(b.getContent());
        b.setContent(htmlContent); // 将转换成html后的文本设置进对象返回给页面
        // 每次访问详情页阅读量累加1
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> getListBlog(Pageable pageable, BlogQuery blog) {
        Page<Blog> blogPage = blogRepository.findAll((Specification<Blog>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 如果标题不为空进行标题的模糊查询
            if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                predicates.add(builder.like(root.get("title"), "%" + blog.getTitle() + "%"));
            }
            // 如果选择了分类按分类进行查询
            if (blog.getTypeId() != null) {
                predicates.add(builder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
            }
            // 按是否推荐进行查询
            if (blog.isRecommend()) {
                predicates.add(builder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
        return blogPage;
    }

    @Override
    public Page<Blog> getListBlog(Pageable pageable, String query) {
        // 模糊查询，需要前后加%
        return blogRepository.findByQuery("%" + query + "%", pageable);
    }

    @Override
    public Page<Blog> getListBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll((Specification<Blog>) (root, query, builder) -> {
            // 按分类进行查询
            Join join = root.join("tags");
            return builder.equal(join.get("id"), tagId);
        }, pageable);
    }

    @Override
    public Page<Blog> getListBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        // 没有id值则是新增
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        // 默认初始阅读数为0
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("该博客不存在");
        }
        // 复制属性，过滤空值的复制，只复制有值的属性
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        // 更新时间
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        // 按更新时间倒序排序
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        // 排序后取出前几个(size)
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findBlogTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public List<Blog> listNewBlogTop(Integer size) {
        // 按更新时间倒序排序
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        // 排序后取出前几个(size)
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findNewBlogTop(pageable);
    }
}
