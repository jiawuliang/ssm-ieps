package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.UserInfo;
import com.ieps.dto.UserAdminDto;

/**
 * Created by ljw
 */
public interface UserAdminService {
    
    ServerResponse getAllUserList(int pageNum, int pageSize, String userNum, int roleId);
    
    ServerResponse searchUserAdminListWithCondition(int pageNum, int pageSize, String userNum, int roleId, UserInfo userInfo);
    
    ServerResponse modifySexWithUserNum(String userNum, Integer sex);
    
    ServerResponse modifyStatusWithUserNum(String userNum, Integer status);
    
    ServerResponse modifyUserByUserNum(UserInfo userInfo);
    
    ServerResponse removeUserByUserNum(String userNum);
    
    ServerResponse addUserAdmin(UserAdminDto userAdminDto, int roleId);
    
    ServerResponse batchRemoveUser(String[] userNums);
    
    ServerResponse getUserInfoByUserName(String userName);
    
    ServerResponse getAllRoleIdWithRoleIdByAdmin(Integer roleId);
    
    ServerResponse batchAddUser(UserAdminDto userAdminDto, Integer roleId, int roleIdAdmin);
    
}
