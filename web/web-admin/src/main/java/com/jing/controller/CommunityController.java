package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseController;
import com.jing.entity.Community;
import com.jing.entity.Dict;
import com.jing.service.CommunityService;
import com.jing.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    @RequestMapping
    public String index(Model model, HttpServletRequest request){
        //分页查询
        Map<String, Object> filters = getFilters(request);
        PageInfo<Community> page = communityService.findPage(filters);
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("areaList",areaList);
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        return "community/index";
    }


    @GetMapping("/create")
    public String create(Model model){
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return "community/create";
    }

    @PostMapping("/save")
    public String save(Community community){
        Integer insert = communityService.insert(community);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model,@PathVariable Long id){
        Community community = communityService.getById(id);
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("community",community);
        model.addAttribute("areaList",areaList);
        return "community/edit";
    }

    @PostMapping("/update")
    public String update(Community community){
        Integer update = communityService.update(community);
        return "common/successPage";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        communityService.delete(id);
        return "redirect:/community";
    }
}
