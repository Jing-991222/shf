package com.jing.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.entity.Admin;
import com.jing.service.AdminService;
import com.jing.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名获取用户信息
        Admin admin = adminService.getByUsername(username);
        if (null == admin){
            throw new UsernameNotFoundException("用户名不存在!");
        }
        //获取用户权限信息
        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        codeList.forEach(code->authorities.add(new SimpleGrantedAuthority(code)));
        return new User(username,admin.getPassword(),authorities);
    }
}
