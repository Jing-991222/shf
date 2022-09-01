package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.AdminRole;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {

    /**
     * 根据用户id获取当前用户的所有的角色的id
     * @param adminId
     * @return
     */
    List<Long> findRoleIdsByAdminId(Long adminId);

    /**
     * 根据用户id删除当前用户的所有角色信息
     * @param adminId
     */
    void deleteByAdminId(Long adminId);
}
