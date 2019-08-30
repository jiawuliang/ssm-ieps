package com.ieps.mapper;

import com.ieps.pojo.Perm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermMapper {
    int deleteByPrimaryKey(Integer permId);

    int insert(Perm record);

    int insertSelective(Perm record);

    Perm selectByPrimaryKey(Integer permId);

    int updateByPrimaryKeySelective(Perm record);

    int updateByPrimaryKey(Perm record);
    
    List<Perm> selectAllPerm();
    
    int deletePermById(@Param("permId") Integer permId, @Param("permType") String permType);
    
    List<Perm> selectAllMenu();
    
    // Perm selectParentByPermId(Integer id);
    
    int batchDeletePerm(Integer[] permIds);
    
    int insertRolePerm(@Param("permId") Integer permId, @Param("roleId") Integer roleId);
    
    
}