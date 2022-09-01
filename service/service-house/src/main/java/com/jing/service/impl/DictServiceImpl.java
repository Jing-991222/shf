package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.dao.DictDao;
import com.jing.entity.Dict;
import com.jing.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //返回数据[{id:2,isParent:true,name:"xxx"}]
        //根据id获取子节点数据
        //判断子节点是否是父节点

        //获取子节点数据列表
        List<Dict> dictList = dictDao.findListByParentId(id);

        //构建Ztree数据
        List<Map<String,Object>> zTree = new ArrayList<>();

        dictList.forEach(dict->{
            Integer count = dictDao.countIsParent(dict.getId());
            Map<String, Object> node = new HashMap<>();
            node.put("id",dict.getId());
            node.put("isParent", count != 0);
            node.put("name",dict.getName());
            zTree.add(node);
        });

        return zTree;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        //根据字典编码获取该编码的数据
        Dict dict = dictDao.getByDictCode(dictCode);
        if (dict == null) return null;
        //讲获取到的该条数据的id作为parentId查询下面的所有子节点
        return dictDao.findListByParentId(dict.getId());
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
