package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseController;
import com.jing.entity.Role;
import com.jing.service.PermissionService;
import com.jing.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {
    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

/* @RequestMapping
public String getAll(Model model){
    List<Role> roleList = roleService.findAll();
    model.addAttribute("roleList",roleList);
    return "role/index";
}*/
    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String findPage(Model model, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        System.out.println(pageInfo);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        return "role/index";
    }


    @GetMapping("/create")
    @PreAuthorize("hasAuthority('role.create')")
    public String create(){
        return "role/crate";
    }

    @PostMapping("/save")
    public String saveRole(Role role){
        Integer len = roleService.insert(role);
        return "common/successPage";
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "role/edit";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('role.edit')")
    public String update(Role role){
        roleService.update(role);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('role.delete')")
    public String delete(@PathVariable Integer id){
        roleService.delete(id);
        return "redirect:/role";
    }


    /**
     * 进入分配权限页面
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("/assignShow/{roleId}")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String assignShow(@PathVariable Long roleId,Model model){
        //获取当前角色的权限信息
        List<Map<String, Object>> zNodes = permissionService.findPermissionsByRoleId(roleId);
        model.addAttribute("zNodes",zNodes);
        model.addAttribute("roleId",roleId);
        return "role/assignShow";
    }

    /**
     * 给指定角色分配指定权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("/assignPermission")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String assignPermission(Long roleId,Long[] permissionIds){
        permissionService.saveRolePermissionRealtionShip(roleId,permissionIds);
        return "common/successPage";
    }
}
