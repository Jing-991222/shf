package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.Community;

import java.util.List;

public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
    String getNameById(Long id);
}
