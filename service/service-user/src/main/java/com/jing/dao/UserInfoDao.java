package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);
}
