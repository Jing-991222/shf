package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.UserInfoDao;
import com.jing.entity.UserInfo;
import com.jing.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    protected BaseDao<UserInfo> getEntityDao() {
        return userInfoDao;
    }

    @Override
    public UserInfo getByPhone(String phone) {

        return userInfoDao.getByPhone(phone);
    }
}
