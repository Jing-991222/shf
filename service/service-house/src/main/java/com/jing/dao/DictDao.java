package com.jing.dao;

import com.jing.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictDao{

    List<Dict> findListByParentId(@Param("parentId") Long id);

    Integer countIsParent(Long id);

    /**
     * 根据字典编码,获取节点数据信息
     * @param dictCode
     * @return
     */
    Dict getByDictCode(@Param("dictCode") String dictCode);

    /**
     * 根据id获取名称
     * @param id
     * @return
     */
    String getNameById(Long id);
}
