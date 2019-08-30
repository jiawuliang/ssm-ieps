package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.Role;
import com.ieps.pojo.User;
import com.ieps.service.RoleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ljw
 */
@Controller
public class RoleAdminController {
    
    @Autowired
    private RoleAdminService roleAdminService;
    
    /**
     * 管理员查看角色信息列表
     * @param page
     * @param role
     * @param limit
     * @param session
     * @param userNum
     * @param roleAdminId
     * @return
     */
    @RequestMapping("/getRoleListByAdmin.do")
    @ResponseBody
    public ServerResponse getRoleListByAdmin(@RequestParam(value = "page", defaultValue = "1") int page, Role role,
                                             @RequestParam(value = "limit", defaultValue = "10") int limit, HttpSession session,
                                             String userNum, int roleAdminId) {
        
        // User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return roleAdminService.getRoleList(page, limit, userNum, roleAdminId, role);
    }
    
    /**
     * 批量删除角色
     * @param roleIds
     * @param userNum
     * @param roleAdminId
     * @param session
     * @return
     */
    @RequestMapping("/batchRemoveRole.do")
    @ResponseBody
    public ServerResponse batchRemoveRole(int[] roleIds, String userNum, int roleAdminId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
    
        System.out.println(userNum + "   " +  roleAdminId);
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return roleAdminService.batchRemoveRole(roleIds, roleAdminId);
    }
    
    /**
     * 根据角色id删除角色
     * @param roleId
     * @param userNum
     * @param roleAdminId
     * @param session
     * @return
     */
    @RequestMapping("/removeRoleByRoleId.do")
    @ResponseBody
    public ServerResponse removeRoleByRoleId(int roleId, String userNum, int roleAdminId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        System.out.println(userNum + "   " +  roleAdminId);
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return roleAdminService.removeRoleByRoleId(roleId, roleAdminId);
    }
    
}
