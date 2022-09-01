package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseController;
import com.jing.entity.UserInfo;
import com.jing.result.Result;
import com.jing.service.UserFollowService;
import com.jing.vo.UserFollowVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController {
    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpServletRequest request){
        //获取当前登录用户的信息
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        userFollowService.follow(userId,houseId);
        return Result.ok();
    }
    @RequestMapping("auth/list/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize,HttpServletRequest request){
        //获取用户信息
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum,pageSize,userId);
        return Result.ok(pageInfo);
    }
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable Long id,HttpServletRequest request){
        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}
