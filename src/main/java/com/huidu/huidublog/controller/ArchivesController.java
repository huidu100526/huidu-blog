package com.huidu.huidublog.controller;

import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @auther huidu
 * @create 2020/2/24 19:24
 * @Description: 归档相关controller
 */
@Controller
public class ArchivesController {
    @Autowired
    private BlogService blogService;

    /**
     * 跳转至归档页面
     */
    @GetMapping("/archives")
    public String archives(Model model) {
        // 获取每个年份对应的所有博客列表
        Map<String, List<Blog>> archiveBlogs = blogService.archiveBlog();
        // 获取所有博客的总数
        Long countBlog = blogService.countBlog();
        model.addAttribute("archiveMap", archiveBlogs);
        model.addAttribute("blogCount", countBlog);
        return "archives";
    }
}
