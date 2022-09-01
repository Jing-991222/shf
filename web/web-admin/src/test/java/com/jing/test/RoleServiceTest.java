package com.jing.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.entity.Role;
import com.jing.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(locations = "classpath:spring/spring-registry.xml.xml")
public class RoleServiceTest {

    @Reference
    private RoleService roleService;

    @Test
    public void testFindAll(){
        List<Role> roleList = roleService.findAll();
        roleList.forEach(System.out::println);
    }

}
