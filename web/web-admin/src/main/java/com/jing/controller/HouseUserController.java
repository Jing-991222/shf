package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.base.BaseController;
import com.jing.entity.HouseUser;
import com.jing.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {
    @Reference
    private HouseUserService houseUserService;

    @RequestMapping("/create")
    public String create(Model model, @RequestParam("houseId") Long houseId){
        model.addAttribute("houseId",houseId);
        return "houseUser/create";
    }

    @PostMapping("/save")
    public String save(HouseUser houseUser){
        houseUserService.insert(houseUser);
        return "common/successPage";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model,@PathVariable Long id){
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return "houseUser/edit";
    }

    @PostMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseUserService.delete(id);
        return "redirect:/house/" + houseId;
    }

}
