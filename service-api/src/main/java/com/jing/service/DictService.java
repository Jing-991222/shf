package com.jing.service;

import com.jing.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService{
    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据 字典编码获取该节点下的所有子节点信息
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);


    /**
     * 根据 parentId 获取所有子集列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    String getNameById(Long id);
}
