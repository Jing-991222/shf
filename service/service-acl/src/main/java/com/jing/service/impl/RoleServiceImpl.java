package com.jing.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jing.base.BaseDao;
import com.jing.base.BaseServiceImpl;
import com.jing.dao.AdminRoleDao;
import com.jing.dao.RoleDao;
import com.jing.entity.AdminRole;
import com.jing.entity.Role;
import com.jing.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll(){
        return roleDao.findAll();
    }

    /**
     * 根据用户的id 获取当前所拥有的角色和没有拥有的角色
     * @param adminId
     * @return
     */
    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询角色列表中所有的角色
        List<Role> allRoleList = roleDao.findAll();

        //查找到当前用户拥有的所有角色的id
        List<Long> existRoleIdList = adminRoleDao.findRoleIdsByAdminId(adminId);

        //对角色分类  已经拥有的,未选择的
        List<Role> noAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();

        for (Role role : allRoleList) {
            //如果包含说明已经分配
            if (existRoleIdList.contains(role.getId())) {
                assignRoleList.add(role);
            }else {
                noAssignRoleList.add(role);
            }

        }

        //将数据返回
        Map<String, Object> map = new HashMap<>();
        map.put("noAssignRoleList",noAssignRoleList);
        map.put("assignRoleList",assignRoleList);
        return map;
    }


    /**
     * 分配角色
     * @param adminId
     * @param roleIds
     */
    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        //首先删除当前用户的所有角色信息
        adminRoleDao.deleteByAdminId(adminId);

        //分配角色
        for (Long roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) continue;
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleDao.insert(adminRole);
        }
    }


    @Override
    protected BaseDao getEntityDao() {
        return roleDao;
    }

}
