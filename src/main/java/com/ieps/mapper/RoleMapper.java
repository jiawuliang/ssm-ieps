package com.ieps.mapper;

import com.ieps.pojo.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    // 学校管理员查找全部的角色名单
    List<Role> selectAllRoleWithRoleAdminId(int roleAdminId);
    
    int batchDeleteRoleWithRoleIds(int[] roleIds);
    
    List<Role> selectRoleIdWithRoleIdByAdmin(Integer roleId);
}