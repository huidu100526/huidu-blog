package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.entity.Tag;
import com.huidu.huidublog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @auther huidu
 * @create 2020/2/22 20:06
 * @Description: 管理后台标签相关controller
 */
@Controller
@RequestMapping("/admin")
public class TagsController {
    @Autowired
    private TagService tagService;

    /**
     * 跳转到标签页面
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        // 分页查询标签列表
        Page<Tag> listTag = tagService.getListTag(pageable);
        model.addAttribute("page", listTag);
        return "admin/tags";
    }

    /**
     * 跳转到新增标签页面
     */
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 新增/修改标签
     */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        // 根据标签名获取标签，判断是否重复添加
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "该标签已存在");
        }
        // 判断表单输入内容是否有误
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        if (id == 0) {
            // 新增标签
            Tag t = tagService.saveTag(tag);
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            // 修改标签
            Tag t = tagService.updateTag(id, tag);
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 跳转至修改标签页面
     */
    @GetMapping("/tags/input/{id}")
    public String editInput(@PathVariable Long id, Model model) {
        // 根据id获取标签
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    /**
     * 删除标签
     */
    @GetMapping("/tags/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
