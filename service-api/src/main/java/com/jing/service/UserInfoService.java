package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);

}
