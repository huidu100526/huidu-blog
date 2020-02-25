package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.entity.Type;
import com.huidu.huidublog.service.TypeService;
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
 * @create 2020/2/22 19:07
 * @Description: 管理后台分类相关controller
 */
@Controller
@RequestMapping("/admin")
public class TypesController {
    @Autowired
    private TypeService typeService;

    /**
     * 跳转到分类页面
     */
    @GetMapping("/types")
    public String toTypesPage(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        // 分页查询分类列表
        Page<Type> listType = typeService.getListType(pageable);
        model.addAttribute("page", listType);
        return "admin/types";
    }

    /**
     * 跳转到新增分类页面
     */
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 新增分类
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        // 根据分类名获取分类，判断是否重复添加
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        // 判断表单输入内容是否有误
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 编辑分类
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        // 根据分类id查询分类
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    /**
     * 修改分类
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id,
                           RedirectAttributes attributes) {
        // 根据分类名获取分类，判断修改后的分类名是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        // 修改分类
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除分类
     */
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
