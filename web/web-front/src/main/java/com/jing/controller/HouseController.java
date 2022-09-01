package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseController;
import com.jing.entity.*;
import com.jing.result.Result;
import com.jing.service.*;
import com.jing.vo.HouseQueryVo;
import com.jing.vo.HouseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {
    @Reference
    private HouseService houseService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result<PageInfo<HouseVo>> findPage(@PathVariable Integer pageNum,
                                              @PathVariable Integer pageSize,
                                              @RequestBody HouseQueryVo houseQueryVo){
        PageInfo<HouseVo> pageInfo = houseService.findPage(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }

    @RequestMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpServletRequest request){
        //获取房屋信息
        House house = houseService.getById(id);
        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取经纪人列表
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        //获取房源图片
        List<HouseImage> houseImage1List = houseImageService.findListByHouseId(id, 1);

        //获取用户信息
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Boolean isFollow=false;
        //是否关注该房屋信息
        if (userInfo!=null){
            isFollow = userFollowService.isFollowed(userInfo.getId(),id);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        map.put("isFollow",isFollow);
        return Result.ok(map);
    }
}
