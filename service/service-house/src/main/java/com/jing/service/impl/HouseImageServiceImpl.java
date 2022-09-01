package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.HouseImageDao;
import com.jing.entity.HouseImage;
import com.jing.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageDao houseImageDao;
    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }


    @Override
    public List<HouseImage> findListByHouseId(Long id,Integer type) {
        return houseImageDao.findListByHouseId(id,type);
    }
}
