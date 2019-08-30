package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.Perm;

import java.util.List;

/**
 * Created by ljw
 */
public interface PermAdminService {
    
    ServerResponse getPermList(int pageNum, int pageSize, Integer roleId, Perm perm);
    
    ServerResponse getPerm();
    
    ServerResponse removePermById(int roleId, Perm perm);
    
    ServerResponse<List<Perm>> getAllMenu();
    
    ServerResponse getPermByPermId(Integer permId);
    
    ServerResponse modifyPermById(Integer roleId, Perm perm);
    
    ServerResponse addPerm(Integer roleId, Perm perm);
    
    ServerResponse batchRemovePerm(Integer roleId, Integer[] permIds);
    
    ServerResponse addRolePerm(Integer[] permIds, Integer roleId, int roleAdminId);
    
    ServerResponse addRolePermWithUserNum(Integer[] permIds, Integer roleId, String userNum);
}
