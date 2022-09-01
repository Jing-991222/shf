package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.base.BaseController;
import com.jing.entity.Admin;
import com.jing.entity.HouseBroker;
import com.jing.service.AdminService;
import com.jing.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {
    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    @RequestMapping("/create")
    public String create(Model model, @RequestParam("houseId") Long houseId){
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        model.addAttribute("houseId",houseId);
        return "houseBroker/create";
    }

    @PostMapping("/save")
    public String save(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model,@PathVariable Long id){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("houseBroker",houseBroker);
        model.addAttribute("adminList",adminList);
        return "houseBroker/edit";
    }
    
    @PostMapping("/update")
    public String update(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseBrokerService.delete(id);
        return "redirect:/house/" + houseId;
    }

}
