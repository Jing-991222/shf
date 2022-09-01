package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.PermissionDao;
import com.jing.dao.RolePermissionDao;
import com.jing.entity.Permission;
import com.jing.entity.RolePermission;
import com.jing.helper.PermissionHelper;
import com.jing.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    /**
     * 根据角色Id查找所有的权限信息
     * @param roleId
     * @return
     */
    @Override
    public List<Map<String, Object>> findPermissionsByRoleId(Long roleId) {
        //在权限表中查找所有的权限信息
        List<Permission> permissionList = permissionDao.findAll();

        //在角色权限表中查找当前角色已经分配的权限id
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);

        //构建zTree树数据
        // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true}
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for (Permission permission : permissionList) {
            Map<String,Object> zNode = new HashMap<>();

            zNode.put("id",permission.getId());
            zNode.put("pId",permission.getParentId());
            zNode.put("name",permission.getName());
            //如果当前角色拥有该权限则选中
            if (permissionIdList.contains(permission.getId())){
                zNode.put("checked",true);
            }
            zNodes.add(zNode);
        }
        return zNodes;
    }


    /**
     * 分配指定权限给指定角色
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {
        //删除该角色的所有权限
        rolePermissionDao.deleteByRoleId(roleId);

        //给指定角色分配权限信息
        for (Long permissionId : permissionIds) {
            if (StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionDao.insert(rolePermission);
        }
    }

    /**
     * 根据用户来分配菜单
     * @param adminId
     * @return
     */
    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;

        //id为1代表超级管理员 拥有所有的菜单
        if (adminId.intValue() == 1){
            permissionList = permissionDao.findAll();
        }else {
            permissionList = permissionDao.findListByAdminId(adminId);
        }

        //将权限数据构建成树状  有节点关系
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> findCodeListByAdminId(Long adminId) {

        //超级管理员具有所有的权限
        if (adminId.intValue() == 1){
            return permissionDao.findAllCode();
        }
        //根据id获取对应的权限
        return permissionDao.findCodeListByAdminId(adminId);
    }
}
