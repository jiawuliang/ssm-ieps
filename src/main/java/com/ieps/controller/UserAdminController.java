package com.ieps.controller;

import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.pojo.Inform;
import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;
import com.ieps.service.UserAdminService;
import com.ieps.dto.UserAdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ljw
 */
@Controller
public class UserAdminController {
    
    @Autowired
    private UserAdminService userAdminService;
    
    @RequestMapping("/getUserAdminList.do")
    @ResponseBody
    public ServerResponse<List<Inform>> getUserAdminList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "limit", defaultValue = "5") int limit, HttpSession session,
                                                         @RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return userAdminService.getAllUserList(page, limit, userNum, roleId);
    }
    
    /**
     *
     * @param page
     * @param limit
     * @param userNum
     * @param userInfo
     * @param roleId
     * @param session
     * @return
     */
    @RequestMapping("/searchUserAdminListWithCondition.do")
    @ResponseBody
    public ServerResponse<List<Inform>> searchUserAdminListWithCondition(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                         @RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                         @RequestParam("userNumAdmin") String userNum, UserInfo userInfo,
                                                                         @RequestParam("roleId") int roleId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return userAdminService.searchUserAdminListWithCondition(page, limit, userNum, roleId, userInfo);
    }
    
    /**
     * 根据userNum修改性别
     * @param userNum
     * @param session
     * @param roleId
     * @param sex
     * @return
     */
    @RequestMapping("/modifySexWithUserNum.do")
    @ResponseBody
    public ServerResponse modifySexWithUserNum(@RequestParam("userNum") String userNum, HttpSession session,
                                               @RequestParam("roleId") int roleId, @RequestParam("sex") Integer sex) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        if (roleId == Const.ROLEID_COLLEGE || roleId == Const.ROLEID_ACADEMY) {
            return userAdminService.modifySexWithUserNum(userNum, sex);
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作该操作！");
    }
    
    /**
     * 根据userNum修改项目状态
     * @param userNum
     * @param session
     * @param roleId
     * @param status
     * @return
     */
    @RequestMapping("/modifyStatusWithUserNum.do")
    @ResponseBody
    public ServerResponse modifyStatusWithUserNum(@RequestParam("userNum") String userNum, HttpSession session,
                                                  @RequestParam("roleId") int roleId, @RequestParam("status") Integer status) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        if (roleId == Const.ROLEID_COLLEGE || roleId == Const.ROLEID_ACADEMY) {
            return userAdminService.modifyStatusWithUserNum(userNum, status);
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作该操作！");
    }
    
    /**
     * 根据userNum修改用户
     * @param userInfo
     * @param roleId
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifyUserByUserNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modifyUserByUserNum(UserInfo userInfo, int roleId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        if (roleId == Const.ROLEID_COLLEGE || roleId == Const.ROLEID_ACADEMY) {
            return userAdminService.modifyUserByUserNum(userInfo);
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作该操作！");
    }
    
    /**
     * 根据userNum删除用户
     * @param userNum
     * @param session
     * @param roleId
     * @return
     */
    @RequestMapping("/removeUserByUserNum.do")
    @ResponseBody
    public ServerResponse removeUserByUserNum(String userNum, HttpSession session, int roleId) {
        
        User user = (User) session.getAttribute("activeUser");
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        if (roleId == Const.ROLEID_COLLEGE || roleId == Const.ROLEID_ACADEMY) {
            return userAdminService.removeUserByUserNum(userNum);
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作该操作！");
    }
    
    /**
     * 添加用户
     * @param userAdminDto
     * @param roleId
     * @param adminRoleId
     * @param session
     * @return
     */
    @RequestMapping("/addUserAdmin.do")
    @ResponseBody
    public ServerResponse addUserAdmin(UserAdminDto userAdminDto, int roleId, int adminRoleId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        if (adminRoleId <= roleId) {
            return ServerResponse.createByErrorMessage("对不起，你没有权限创建该角色的操作！");
        }
        
        if (adminRoleId == Const.ROLEID_COLLEGE || adminRoleId == Const.ROLEID_ACADEMY) {
            return userAdminService.addUserAdmin(userAdminDto, roleId);
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作该操作！");
    }
    
    /**
     * 批量删除用户
     * @param userNums
     * @param roleId
     * @param session
     * @return
     */
    @RequestMapping("/batchRemoveUser.do")
    @ResponseBody
    public ServerResponse batchRemoveUser(@RequestParam("userNums") String[] userNums, int roleId, HttpSession session) {
        
        // User user = (User) session.getAttribute("activeUser");
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        if (roleId == Const.ROLEID_COLLEGE || roleId == Const.ROLEID_ACADEMY) {
            return userAdminService.batchRemoveUser(userNums);
        }
        
        return ServerResponse.createByErrorMessage("对不起，你没有权限操作该操作！");
    }
    
    /**
     * 根据姓名获取用户信息
     * @param userName
     * @param session
     * @return
     */
    @RequestMapping("/getUserInfoByUserName.do")
    @ResponseBody
    public ServerResponse getUserInfoByUserName(@RequestParam("userName") String userName, HttpSession session) {
        
        // User user = (User) session.getAttribute("activeUser");
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return userAdminService.getUserInfoByUserName(userName);
    }
    
    /**
     * 管理员可以管理的角色列表
     * @param userName
     * @param session
     * @return
     */
    @RequestMapping("/getAllRoleIdWithRoleIdByAdmin.do")
    @ResponseBody
    public ServerResponse getAllRoleIdWithRoleIdByAdmin(Integer roleId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return userAdminService.getAllRoleIdWithRoleIdByAdmin(roleId);
    }
    
    /**
     * 批量注册用户
     * @param userAdminDto
     * @param roleId
     * @param roleIdAdmin
     * @param session
     * @return
     */
    @RequestMapping("/batchAddUser.do")
    @ResponseBody
    public ServerResponse batchAddUser(UserAdminDto userAdminDto, @RequestParam("roleId") Integer roleId,
                                       @RequestParam("roleIdAdmin") int roleIdAdmin, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        // if (user == null) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return userAdminService.batchAddUser(userAdminDto, roleId, roleIdAdmin);
    }
    
    
    
    
    
    
    
}
