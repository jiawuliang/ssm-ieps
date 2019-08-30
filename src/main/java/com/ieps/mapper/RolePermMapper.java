package com.ieps.mapper;

import com.ieps.pojo.Perm;
import com.ieps.pojo.RolePerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePerm record);

    int insertSelective(RolePerm record);

    RolePerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePerm record);

    int updateByPrimaryKey(RolePerm record);
    
    // 获取菜单权限
    List<RolePerm> getPerm(Integer roleId);
    
    List<Perm> selectPermList(@Param("roleId") Integer roleId, @Param("perm") Perm perm);
    
    // 角色授权
    int insertRolePerm(@Param("permId") Integer permId, @Param("roleId") Integer roleId);
}