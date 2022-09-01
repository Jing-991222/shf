package com.jing.test;

import com.jing.service.DictService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;

@SpringJUnitConfig(locations = "classpath:spring/spring-*.xml")
public class DIctServiceTest {
    @Autowired
    private DictService dictService;
    @Test
    public void findByParentId(){
        List<Map<String, Object>> zNodes = dictService.findZnodes(1L);
        zNodes.forEach(node->{
            node.forEach((k,v)->{
                System.out.println(k + "=" + v);
            });
        });
    }
}
