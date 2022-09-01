package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.HouseUserDao;
import com.jing.entity.HouseUser;
import com.jing.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    private HouseUserDao houseUserDao;
    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }

    @Override
    public List<HouseUser> findListByHouseId(Long id) {
        return houseUserDao.findListByHouseId(id);
    }
}
