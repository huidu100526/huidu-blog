package com.huidu.huidublog.controller;

import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Tag;
import com.huidu.huidublog.service.BlogService;
import com.huidu.huidublog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/24 20:05
 * @Description: 标签相关controller
 */
@Controller
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    /**
     * 跳转至标签页面
     */
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model) {
        // 查询所有标签，需排序
        List<Tag> tags = tagService.getListTagTop(100);
        // 如果等于-1，则是从首页标签进入，默认展示所属第一个标签的所有博客
        if (id == -1) {
            id = tags.get(0).getId();
        }
        // 按标签id分页查询所有博客
        Page<Blog> listBlog = blogService.getListBlog(pageable, id);
        model.addAttribute("tags", tags);
        model.addAttribute("page", listBlog);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
