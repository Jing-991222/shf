package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
    String getNameById(Long id);
}
