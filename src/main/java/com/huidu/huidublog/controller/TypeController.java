package com.huidu.huidublog.controller;

import com.huidu.huidublog.VO.BlogQuery;
import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.service.BlogService;
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

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/24 18:55
 * @Description: 分类相关controller
 */
@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 跳转至分类页面
     */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        // 查询所有分类，需排序
        List<Type> types = typeService.getListTypeTop(100);
        // 如果等于-1，则是从首页标签进入，默认展示所属第一个分类的所有博客
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        // 按分类id分页查询所有博客
        Page<Blog> listBlog = blogService.getListBlog(pageable, blogQuery);
        model.addAttribute("types", types);
        model.addAttribute("page", listBlog);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
