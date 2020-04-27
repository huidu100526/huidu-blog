package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.entity.Role;
import com.huidu.huidublog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/28 20:50
 * @Description: 后台管理角色相关controller
 */
@Controller
@RequestMapping("/admin")
public class RolesController {
    @Autowired
    private RoleService roleService;

    /**
     * 跳转到角色页面
     */
    @GetMapping("/roles")
    public String tags(Model model) {
        // 查询所有角色
        List<Role> listRole = roleService.getListRole();
        model.addAttribute("listRole", listRole);
        return "admin/roles";
    }

    /**
     * 跳转到新增角色页面
     */
    @GetMapping("/roles/input")
    public String input(Model model) {
        model.addAttribute("role", new Role());
        return "admin/roles-input";
    }

    /**
     * 新增/修改角色
     */
    @PostMapping("/roles/{id}")
    public String post(@Valid Role role, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        // 根据角色名获取角色，判断是否重复添加
        Role role1 = roleService.getRoleByName(role.getName());
        if (role1 != null) {
            result.rejectValue("name", "nameError", "该角色已存在");
        }
        // 判断表单输入内容是否有误
        if (result.hasErrors()) {
            return "admin/roles-input";
        }
        if (id == 0) {
            // 新增角色
            Role r = roleService.saveRole(role);
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            // 修改角色
            Role r = roleService.updateRole(id, role);
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/roles";
    }

    /**
     * 跳转至修改角色页面
     */
    @GetMapping("/roles/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        // 根据id获取角色
        Role role = roleService.getRole(id);
        model.addAttribute("role", role);
        return "admin/roles-input";
    }

    /**
     * 删除角色
     */
    @GetMapping("/roles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        roleService.deleteRole(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/roles";
    }
}
