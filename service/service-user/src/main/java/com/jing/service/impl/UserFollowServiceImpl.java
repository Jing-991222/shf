package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.UserFollowDao;
import com.jing.entity.UserFollow;
import com.jing.service.DictService;
import com.jing.service.UserFollowService;
import com.jing.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;
    @Reference
    private DictService dictService;


    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer count = userFollowDao.countByUserIdAndHouseId(userId,houseId);
        return count > 0;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum,pageSize);
        Page<UserFollowVo> page = userFollowDao.findListPage(userId);
        List<UserFollowVo> result = page.getResult();
        for (UserFollowVo userFollowVo : result) {
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            userFollowVo.setDirectionName(directionName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setHouseTypeName(houseTypeName);
        }
        return new PageInfo<>(page,10);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }
}
