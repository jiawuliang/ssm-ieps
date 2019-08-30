package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.Role;

/**
 * Created by ljw
 */
public interface RoleAdminService {
    
    ServerResponse getRoleList(int pageNum, int pageSize, String userNum, int roleAdminId, Role role);
    
    ServerResponse batchRemoveRole(int[] roleIds, int roleAdminId);
    
    ServerResponse removeRoleByRoleId(int roleId, int roleAdminId);
    
}
