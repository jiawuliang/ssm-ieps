package com.ieps.service.impl;

import com.ieps.common.ServerResponse;
import com.ieps.mapper.RolePermMapper;
import com.ieps.pojo.RolePerm;
import com.ieps.service.RolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class RolePermServiceImpl implements RolePermService {
    
    @Autowired
    private RolePermMapper rolePermMapper;
    
    @Override
    public ServerResponse<List<RolePerm>> getMenu(Integer roleId) {
        
        List<RolePerm> rolePermList = rolePermMapper.getPerm(roleId);
    
        System.out.println(rolePermList.get(0).getPermList());
        
        if (rolePermList.size() == 0) {
            return ServerResponse.createByErrorMessage("加载菜单出错，请重试！");
        }
        
        return ServerResponse.createBySuccess("菜单加载完成，请尽情享用！", rolePermList);
    }
}
