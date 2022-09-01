package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * 给角色分配权限
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * 根据角色Id获取所有的权限信息
     * @param roleId
     * @return
     */
    List<Map<String,Object>> findPermissionsByRoleId (Long roleId);

    /**
     * 分配给指定角色权限信息
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermissionRealtionShip(Long roleId,Long[] permissionIds);

    /**
     * 根据用户id获取左侧菜单
     */
    List<Permission> findMenuPermissionByAdminId (Long adminId);


    /**
     * 根据用户id获取用户权限
     * @param adminId
     * @return
     */
    List<String> findCodeListByAdminId(Long adminId);
}
