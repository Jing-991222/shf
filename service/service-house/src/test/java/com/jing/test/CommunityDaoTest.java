package com.jing.test;

import com.jing.dao.CommunityDao;
import com.jing.entity.Community;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(locations = "classpath:spring/spring-*.xml")
public class CommunityDaoTest {

    @Autowired
    private CommunityDao communityDao;

    @Test
    public void findAll(){
        List<Community> all = communityDao.findAll();
        all.forEach(System.out::println);
    }
}
