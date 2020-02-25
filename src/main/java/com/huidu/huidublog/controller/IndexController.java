package com.huidu.huidublog.controller;

import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Tag;
import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.service.BlogService;
import com.huidu.huidublog.service.TagService;
import com.huidu.huidublog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/21 19:59
 * @Description: 博客首页controller
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 博客首页
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        // 分页查询所有博客
        Page<Blog> listBlog = blogService.getListBlog(pageable);
        // 获取使用最多的前6个分类
        List<Type> typeTop = typeService.getListTypeTop(6);
        // 获取使用最多的前6个标签
        List<Tag> tagTop = tagService.getListTagTop(6);
        // 获取最新推荐的前6篇文章
        List<Blog> recommendBlogTop = blogService.listRecommendBlogTop(6);
        model.addAttribute("page", listBlog);
        model.addAttribute("types", typeTop);
        model.addAttribute("tags", tagTop);
        model.addAttribute("recommendBlogs", recommendBlogTop);
        return "index";
    }

    /**
     * 博客首页全局搜索查询
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        // 按条件查询博客列表
        Page<Blog> listBlog = blogService.getListBlog(pageable, query);
        model.addAttribute("page", listBlog);
        // 返回查询条件至页面回显
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * 跳转至某篇博客详情页面
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        // 根据博客id查询博客
        Blog blog = blogService.getBlobAndConvert(id);
        model.addAttribute("blog", blog);
        return "blog";
    }

    /**
     * 首页尾部用于获取最新发布的三篇博客
     */
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        // 查询最新发布的三篇博客
        List<Blog> blogs = blogService.listNewBlogTop(3);
        model.addAttribute("newblogs", blogs);
        return "commons/fragments :: newblogList";
    }
}
