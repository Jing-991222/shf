package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.RolePermission;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission> {

    /**
     * 根据角色id查找出所有的权限信息id
     * @param roleId
     * @return
     */
    List<Long> findPermissionIdListByRoleId(Long roleId);


    /**
     * 根据角色id删除该角色的所有权限信息
     * @param roleId
     */
    void deleteByRoleId(Long roleId);
}
