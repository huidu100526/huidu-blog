package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.service.*;
import com.huidu.huidublog.vo.BlogQuery;
import com.huidu.huidublog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserLikeBlogService userLikeBlogService;

    /**
     * 跳转到博客列表页面，进行分页显示博客列表
     */
    @GetMapping("/blogs")
    public String toblogsPage(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        // 查询所有分类
        List<Type> listType = typeService.getListType();
        // 分页查询所有博客
        Page<Blog> listBlog = blogService.getListBlog(pageable, false);
        model.addAttribute("page", listBlog);
        model.addAttribute("types", listType);
        return "admin/blogs";
    }

    /**
     * 新增/修改博客
     */
    @PostMapping("/blogs")
    public String post(@AuthenticationPrincipal Principal principal, Blog blog, RedirectAttributes attributes) {
        blog.setUser(userService.findByUsername(principal.getName())); // 设置用户
        blog.setType(typeService.getType(blog.getType().getId())); // 设置分类
        blog.setTags(tagService.getListTag(blog.getTagIds())); // 设置标签
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
     * 当进行条件搜索或下一页时，进行片段数据传递
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
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
     * 删除博客
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
//        // 判断该博客是否存在相关点赞记录
//        if (userLikeBlogService.hasLikeByBlogId(id)) {
//            // 如果存在则删除相关记录
//            userLikeBlogService.deleteBlogLikeByBolgId(id);
//        }
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 上传图片接口
     */
    @PostMapping("/blogs/upload")
    @ResponseBody
    public ResultVO upload(MultipartFile file) {
        String imageUrl = userService.uploadImage(file);
        return ResultVO.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), imageUrl);
    }
}
