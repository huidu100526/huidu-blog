package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.VO.BlogQuery;
import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.entity.User;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 15:51
 * @Description: 管理后台博客相关controller
 */
@Controller
@RequestMapping("/admin")
public class BlogsController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 跳转到博客列表页面，进行分页显示博客列表
     */
    @GetMapping("/blogs")
    public String toblogsPage(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              BlogQuery blog, Model model) {
        // 查询所有分类
        List<Type> listType = typeService.getListType();
        // 分页查询所有博客
        Page<Blog> listBlog = blogService.getListBlog(pageable, blog);
        model.addAttribute("page", listBlog);
        model.addAttribute("types", listType);
        return "admin/blogs";
    }

    /**
     * 当进行条件搜索或下一页时，进行片段数据传递
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        Page<Blog> listBlog = blogService.getListBlog(pageable, blog);
        model.addAttribute("page", listBlog);
        // 指定blogList片段，只刷新这个片段中的数据
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转到新增博客页面
     */
    @GetMapping("/blogs/input")
    public String input(Model model) {
        // 获取到分类和标签
        model.addAttribute("types", typeService.getListType());
        model.addAttribute("tags", tagService.getListTag());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    /**
     * 跳转到编辑博客页面
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.getListType());
        model.addAttribute("tags", tagService.getListTag());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    /**
     * 新增/修改博客
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.getListTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            // 新增博客
            b = blogService.saveBlog(blog);
        } else {
            // 修改博客
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 删除博客
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 首页尾部用于获取最新发布的三篇博客
     */
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        // 查询最新发布的三篇博客
        List<Blog> blogs = blogService.listNewBlogTop(3);
        model.addAttribute("newblogs", blogs);
        return "commons/fragments_admin :: newblogList";
    }
}
