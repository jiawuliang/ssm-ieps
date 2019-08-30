package com.ieps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.mapper.RoleMapper;
import com.ieps.pojo.Role;
import com.ieps.service.RoleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class RoleAdminServiceImpl implements RoleAdminService {
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    public ServerResponse getRoleList(int pageNum, int pageSize, String userNum, int roleAdminId, Role role) {
        
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        
        List<Role> roleList = Lists.newArrayList();
        
        if (Const.ROLEID_ACADEMY == roleAdminId || Const.ROLEID_COLLEGE == roleAdminId) {
            roleList = roleMapper.selectAllRoleWithRoleAdminId(roleAdminId);
        }
        
        if (roleList.size() == 0) {
            return ServerResponse.createByErrorMessage("对不起，你没有权限查看角色列表，请重试！");
        }
        
        // 设置分页
        PageInfo pageInfo = new PageInfo(roleList);
        pageInfo.setList(roleList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse batchRemoveRole(int[] roleIds, int roleAdminId) {
        
        if (Const.ROLEID_COLLEGE == roleAdminId) {
            int result = roleMapper.batchDeleteRoleWithRoleIds(roleIds);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除失败，请重新尝试！");
            }
            
            return ServerResponse.createBySuccessMessage("恭喜你，批量删除完成！");
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限执行该操作！");
    }
    
    @Override
    public ServerResponse removeRoleByRoleId(int roleId, int roleAdminId) {
        if (Const.ROLEID_COLLEGE == roleAdminId) {
            int result = roleMapper.deleteByPrimaryKey(roleId);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除失败，请重新尝试！");
            }
        
            return ServerResponse.createBySuccessMessage("恭喜你，删除" + roleId + "完成！");
        }
    
        return ServerResponse.createByErrorMessage("对不起，你没有权限执行该操作！");
        
    }
    
}
