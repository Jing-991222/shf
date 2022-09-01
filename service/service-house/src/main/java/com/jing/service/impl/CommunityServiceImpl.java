package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.CommunityDao;
import com.jing.entity.Community;
import com.jing.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;
    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public String getNameById(Long id) {
        return communityDao.getNameById(id);
    }
}
