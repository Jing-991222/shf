package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.entity.Admin;
import com.jing.entity.Permission;
import com.jing.service.AdminService;
import com.jing.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
    @Reference
    private PermissionService permissionService;
    @Reference
    private AdminService adminService;

    @RequestMapping("/")
    public String index(Model model){
        //先定义AdminId为1
        //Long adminId = 1L;
        //从springSecurity的上下文中获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        //获取用户信息
        Admin admin = adminService.getByUsername(user.getUsername());
        //根据用户id获取菜单列表
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());

        model.addAttribute("admin",admin);
        model.addAttribute("permissionList",permissionList);

        return "frame/index";

    }

    @RequestMapping("/toLogin")
    public String login(){
        return "frame/login";
    }


    /**
     * 获取当前登录信息
     * @return
     */
    @GetMapping("/getInfo")
    @ResponseBody
    public Object getInfo(){
        //从springSecurity的上下文中获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //返回认证信息的Principal(当前用户的信息)
        return authentication.getPrincipal();
    }

    @GetMapping("/auth")
    public String auth(){
        return "frame/auth";
    }
}
