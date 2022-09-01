package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseController;
import com.jing.entity.Admin;
import com.jing.service.AdminService;
import com.jing.service.RoleService;
import com.jing.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);

        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return "admin/index";
    }

    @GetMapping("/create")
    public String create() {
        return "admin/create";
    }


    @PostMapping("/save")
    public String save(Admin admin) {
        //设置默认头像
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        //密码加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);

        return "common/successPage";
    }

    /**
     * 进入编辑页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model,@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return "admin/edit";
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public String update(Admin admin) {

        adminService.update(admin);

        return "common/successPage";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        adminService.delete(id);
        return "redirect:/admin";
    }

    /**
     * 跳转到上传头像页面
     * @param id 用户的id
     * @param model
     * @return
     */
    @RequestMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "admin/upload";
    }

    /**
     * 上传用户头像
     * @param id
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id, MultipartFile file) throws IOException {
        Admin admin = adminService.getById(id);
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        fileName += originalFilename.substring(originalFilename.lastIndexOf("."));
        QiniuUtil.upload2Qiniu(file.getBytes(),fileName);
        String url = "http://rh76lbt0k.hb-bkt.clouddn.com/" + fileName;
        admin.setHeadUrl(url);
        adminService.update(admin);
        return "common/successPage";
    }

    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(Model model,@PathVariable Long adminId){
        //获取用户的角色信息
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        model.addAllAttributes(roleMap);
        model.addAttribute("adminId",adminId);
        return "admin/assignShow";
    }


    /**
     * 根据用户分配角色信息
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){
        roleService.saveUserRoleRealtionShip(adminId,roleIds);
        return "common/successPage";
    }
}
