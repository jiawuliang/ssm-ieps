package com.ieps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.mapper.PermMapper;
import com.ieps.mapper.UserRoleMapper;
import com.ieps.pojo.Perm;
import com.ieps.pojo.UserRole;
import com.ieps.service.PermAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class PermAdminServiceImpl implements PermAdminService {
    
    @Autowired
    private PermMapper permMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Override
    public ServerResponse getPermList(int pageNum, int pageSize, Integer roleId, Perm perm) {
        
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        
        List<Perm> permList = permMapper.selectAllPerm();
        
        if (permList.size() == 0) {
            return ServerResponse.createByErrorMessage("加载菜单出错，请重试！");
        }
        
        // 设置分页
        PageInfo pageInfo = new PageInfo(permList);
        pageInfo.setList(permList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse getPerm() {
        List<Perm> permList = permMapper.selectAllPerm();
        
        if (permList.size() == 0) {
            return ServerResponse.createByErrorMessage("加载菜单出错，请重试！");
        }
        
        return ServerResponse.createBySuccess(permList);
    }
    
    @Override
    public ServerResponse removePermById(int roleId, Perm perm) {
        if (roleId == Const.ROLEID_COLLEGE) {
            if (permMapper.deletePermById(perm.getPermId(), perm.getPermType()) > 0) {
                return ServerResponse.createBySuccessMessage("删除" + perm.getPermId() + "成功");
            } else {
                return ServerResponse.createByErrorMessage("删除permId：" + perm.getParentId() + "失败!");
            }
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作！");
    }
    
    @Override
    public ServerResponse<List<Perm>> getAllMenu() {
        return ServerResponse.createBySuccess(permMapper.selectAllMenu());
    }
    
    @Override
    public ServerResponse getPermByPermId(Integer permId) {
        Perm perm = permMapper.selectByPrimaryKey(permId);
        if (perm == null) {
            return ServerResponse.createByErrorMessage("查找父节点信息失败，请重新尝试！");
        }
        
        return ServerResponse.createBySuccess(perm);
    }
    
    @Override
    public ServerResponse modifyPermById(Integer roleId, Perm perm) {
        if (roleId == Const.ROLEID_COLLEGE) {
            if (permMapper.updateByPrimaryKeySelective(perm) > 0) {
                return ServerResponse.createBySuccess();
            } else {
                return ServerResponse.createByErrorMessage("修改permId：" + perm.getPermId() + "失败!");
            }
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作！");
    }
    
    @Override
    public ServerResponse addPerm(Integer roleId, Perm perm) {
        
        System.out.println(perm);
        
        perm.setPermDesc(perm.getPermName());
        
        if (roleId == Const.ROLEID_COLLEGE) {
            
            if (perm.getIcon() == "" || perm.getIcon() == null) {
                perm.setIcon("&#xe63c;");
            }
            
            if (permMapper.insertSelective(perm) > 0) {
                return ServerResponse.createBySuccess();
            } else {
                return ServerResponse.createByErrorMessage("新增permId：" + perm.getPermId() + "失败!");
            }
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作！");
    }
    
    @Override
    public ServerResponse batchRemovePerm(Integer roleId, Integer[] permIds) {
        if (roleId == Const.ROLEID_COLLEGE) {
            if (permMapper.batchDeletePerm(permIds) > 0) {
                return ServerResponse.createBySuccess();
            } else {
                return ServerResponse.createByErrorMessage("对不起，新增失败，请重新尝试！");
            }
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作！");
    }
    
    @Override
    public ServerResponse addRolePerm(Integer[] permIds, Integer roleId, int roleAdminId) {
        
        if (roleAdminId == Const.ROLEID_COLLEGE) {
            
            for (int i = 0; i < permIds.length; i++) {
                if (permMapper.insertRolePerm(permIds[i], roleId) <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，授权失败！");
                }
            }
            
            return ServerResponse.createByErrorMessage("恭喜你，授权成功！");
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限执行角色授权操作！");
    }
    
    @Override
    public ServerResponse addRolePermWithUserNum(Integer[] permIds, Integer roleId, String userNum) {
        
        if (roleId == Const.ROLEID_COLLEGE) {
    
            UserRole userRole = userRoleMapper.selectRoleWithUserNum(userNum);
            if (userRole == null) {
                return ServerResponse.createByErrorMessage("对不起，该用户目前角色出错，请检查数据！");
            }
        
            for (int i = 0; i < permIds.length; i++) {
                if (permMapper.insertRolePerm(permIds[i], userRole.getRoleId()) <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，授权失败！");
                }
            }
        
            return ServerResponse.createByErrorMessage("恭喜你，授权成功！");
        }
    
        return ServerResponse.createByErrorMessage("对不起，你没有权限执行角色授权操作！");
    }
    
    
}
