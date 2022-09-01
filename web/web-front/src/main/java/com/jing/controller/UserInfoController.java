package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.base.BaseController;
import com.jing.entity.UserInfo;
import com.jing.result.Result;
import com.jing.result.ResultCodeEnum;
import com.jing.service.UserInfoService;
import com.jing.util.MD5;
import com.jing.util.ValidateCodeUtils;
import com.jing.vo.LoginVo;
import com.jing.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
    @Reference
    private UserInfoService userInfoService;


    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone, HttpServletRequest request){
        //随机生成四位验证码
        String validateCode = ValidateCodeUtils.generateValidateCode4String(4);
        //将验证码保存在session中,注册时进行比较
        request.getSession().setAttribute("CODE",validateCode);
        return Result.ok(validateCode);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo,HttpServletRequest request){
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();

        //非空校验
        if (StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickName)
                || StringUtils.isEmpty(code)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //校验验证码

        Object validateCode = request.getSession().getAttribute("CODE");
        if (!code.equals(validateCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        //判断手机号是否被注册
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (userInfo!=null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //正常注册
        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setPhone(phone);
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpServletRequest request){
        String password = loginVo.getPassword();
        String phone = loginVo.getPhone();
        if (StringUtils.isEmpty(password)||StringUtils.isEmpty(phone)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (userInfo==null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (!MD5.encrypt(password).equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        if (userInfo.getStatus()!=1){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //将用户放入session中,用来判断用户是否登录
        request.getSession().setAttribute("USER",userInfo);
        Map map =new HashMap<>();
        map.put("nickName",userInfo.getNickName());
        return Result.ok(map);
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().invalidate();
        return Result.ok();
    }

}
