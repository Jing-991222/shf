package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseController;
import com.jing.entity.*;
import com.jing.service.*;
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
@RequestMapping("/house")
public class HouseController extends BaseController {
    @Reference
    private HouseService houseService;
    @Reference
    private DictService dictService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping
    public String index(Model model, HttpServletRequest request){
        //分页查询
        Map<String, Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
//        //获取所有的小区
//        List<Community> communityList = communityService.findAll();
//        model.addAttribute("communityList",communityList);
//        //获取房屋用途
//        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
//        model.addAttribute("houseUseList",houseUseList);
//        //获取户型
//        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
//        model.addAttribute("houseTypeList",houseTypeList);
//        //获取楼层
//        List<Dict> floorList = dictService.findListByDictCode("floor");
//        model.addAttribute("floorList",floorList);
//        //获取建筑结构
//        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
//        model.addAttribute("buildStructureList",buildStructureList);
//        //获取朝向
//        List<Dict> directionList = dictService.findListByDictCode("direction");
//        model.addAttribute("directionList",directionList);
//        //获取装修情况
//        List<Dict> decorationList = dictService.findListByDictCode("decoration");
//        model.addAttribute("decorationList",decorationList);
        saveHouseInfo(model);

        return "house/index";
    }


    @GetMapping("/create")
    public String create(Model model){
        saveHouseInfo(model);
        return "house/create";
    }

    @PostMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        saveHouseInfo(model);
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        return "house/edit";
    }
    @PostMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status){
        houseService.publish(id,status);
        return "redirect:/house";
    }


    @GetMapping("/{id}")
    public String show(Model model,@PathVariable Long id){
        //根据id拿到房源信息
        House house = houseService.getById(id);
        //拿到小区信息
        Community community = communityService.getById(house.getCommunityId());
        //经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(house.getId());
        model.addAttribute("houseBrokerList",houseBrokerList);
        //房东
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(house.getId());
        model.addAttribute("houseUserList",houseUserList);
        //图片
        List<HouseImage> houseImage1List = houseImageService.findListByHouseId(house.getId(), 1);
        List<HouseImage> houseImage2List = houseImageService.findListByHouseId(house.getId(), 2);
        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        model.addAttribute("house",house);
        model.addAttribute("community",community);

        return "house/show";
    }


    private void saveHouseInfo(Model model){
        //获取所有的小区
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList",communityList);
        //获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        model.addAttribute("houseUseList",houseUseList);
        //获取户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        model.addAttribute("houseTypeList",houseTypeList);
        //获取楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        model.addAttribute("floorList",floorList);
        //获取建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        model.addAttribute("buildStructureList",buildStructureList);
        //获取朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        model.addAttribute("directionList",directionList);
        //获取装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        model.addAttribute("decorationList",decorationList);

    }
}
