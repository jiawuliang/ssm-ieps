package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.Perm;
import com.ieps.service.PermAdminService;
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
public class PermAdminController {
    
    
    @Autowired
    private PermAdminService permAdminService;
    
    @RequestMapping("/getPermListWithCondition.do")
    @ResponseBody
    public ServerResponse<List<Perm>> getPermListWithCondition(@RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "limit", defaultValue = "5") int limit, HttpSession session,
                                                               @RequestParam("userNum") String userNum, @RequestParam("roleId") Integer roleId,
                                                               Perm perm) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        //
        // return informService.searchInformListWithCondition(page, limit, userNum, roleId, inform);
        return permAdminService.getPermList(page, limit, roleId, perm);
    }
    
    /**
     * 条件搜索权限列表
     * @param session
     * @return
     */
    @RequestMapping("/getPermWithCondition.do")
    @ResponseBody
    public ServerResponse getPermWithCondition(HttpSession session) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.getPerm();
    }
    
    /**
     * 根据id删除权限
     * @param userNum
     * @param roleId
     * @param session
     * @param perm
     * @return
     */
    @RequestMapping("/removePermById.do")
    @ResponseBody
    public ServerResponse<List<Perm>> removePermById(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                     HttpSession session, Perm perm) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.removePermById(roleId, perm);
    }
    
    /**
     * 获取所有的菜单
     * @return
     */
    @RequestMapping("/getAllMenu.do")
    @ResponseBody
    public ServerResponse<List<Perm>> getAllMenu() {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.getAllMenu();
    }
    
    /**
     * 根据permId获取权限
     * @param userNum
     * @param roleId
     * @param session
     * @param permId
     * @return
     */
    @RequestMapping("/getPermByPermId.do")
    @ResponseBody
    public ServerResponse<Perm> getPermByPermId(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                    HttpSession session, Integer permId) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.getPermByPermId(permId);
    }
    
    /**
     * 通过permId修改权限
     * @param userNum
     * @param roleId
     * @param session
     * @param perm
     * @return
     */
    @RequestMapping("/modifyPemrById.do")
    @ResponseBody
    public ServerResponse modifyPemrById(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                HttpSession session, Perm perm) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.modifyPermById(roleId, perm);
    }
    
    /**
     * 添加权限
     * @param userNum
     * @param roleId
     * @param session
     * @param perm
     * @return
     */
    @RequestMapping("/addPerm.do")
    @ResponseBody
    public ServerResponse addPerm(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                               HttpSession session, Perm perm) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.addPerm(roleId, perm);
    }
    
    /**
     * 批量删除权限
     * @param userNum
     * @param roleId
     * @param session
     * @param permIds
     * @return
     */
    @RequestMapping("/batchRemovePerm.do")
    @ResponseBody
    public ServerResponse batchRemovePerm(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                  HttpSession session, Integer[] permIds) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        return permAdminService.batchRemovePerm(roleId, permIds);
    }
    
    /**
     * 角色授权
     * @param permIds
     * @param roleId
     * @param userNum
     * @param roleAdminId
     * @return
     */
    @RequestMapping(value = "/addRolePerm.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addRolePerm(Integer[] permIds, Integer roleId, String userNum, int roleAdminId) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
    
        System.out.println(permIds + "\t" + roleId);

        return permAdminService.addRolePerm(permIds, roleId, roleAdminId);
    }
    
    /**
     * 用户授权
     * @param permIds
     * @param roleId
     * @param userNum
     * @param userAdminNum
     * @return
     */
    @RequestMapping(value = "/addRolePermWithUserNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addRolePermWithUserNum(Integer[] permIds, Integer roleId, String userNum, String userAdminNum) {
        
        // User user = (User) session.getAttribute("activeUser");
        //
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        System.out.println(permIds + "\t" + roleId);
        
        return permAdminService.addRolePermWithUserNum(permIds, roleId, userNum);
    }
    
    
}
