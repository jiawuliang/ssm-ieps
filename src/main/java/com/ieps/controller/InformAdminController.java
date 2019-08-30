package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.Inform;
import com.ieps.pojo.User;
import com.ieps.service.InformService;
import com.ieps.service.UserInfoService;
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
public class InformAdminController {
    
    @Autowired
    private InformService informService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    /**
     * 根据userNum获取通知公告
     * @param userNum
     * @return
     */
    @RequestMapping("/getInformByUserNum.do")
    @ResponseBody
    public ServerResponse<List<Inform>> getInformByUserNum(String userNum) {
        return informService.getInformByUserNum(userNum);
    }
    
    /**
     * 获取全部的通知公告列表并分页显示
     * @param page
     * @param limit
     * @param session
     * @param userNum
     * @param roleId
     * @return
     */
    @RequestMapping("/getAllInformList.do")
    @ResponseBody
    public ServerResponse<List<Inform>> getAllInformList(@RequestParam(value = "page",defaultValue = "1") int page,
                                 @RequestParam(value = "limit",defaultValue = "10") int limit, HttpSession session,
                                 @RequestParam(value = "userNum", required = false) String userNum, @RequestParam("roleId") Integer roleId) {
    
        User user = (User) session.getAttribute("activeUser");
    
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return informService.getAllInformList(page, limit, userNum, roleId);
        
    }
    
    /**
     * 模糊搜索文件并分页显示
     * @param page
     * @param limit
     * @param session
     * @param userNum
     * @param roleId
     * @param inform
     * @return
     */
    @RequestMapping("/searchInformListWithCondition.do")
    @ResponseBody
    public ServerResponse<List<Inform>> searchInformListWithCondition(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "limit", defaultValue = "5") int limit, HttpSession session,
                                                         @RequestParam("userNum") String userNum, @RequestParam("roleId") Integer roleId,
                                                         Inform inform) {
    
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return informService.searchInformListWithCondition(page, limit, userNum, roleId, inform);
    }
    
    /**
     * 根据id删除通知公告
     * @param userNum
     * @param id
     * @param roleId
     * @param session
     * @return
     */
    @RequestMapping(value = "/removeInformById.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse removeInformById(@RequestParam("userNum") String userNum, @RequestParam("id") Integer id,
                                           @RequestParam("roleId") Integer roleId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");

        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return informService.removeInformById(id, roleId);
    }
    
    /**
     * 通过id修改通知公告
     * @param userNum
     * @param inform
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifyInformById.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modifyInformById(@RequestParam("userNum") String userNum, Inform inform,
                                           HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return informService.modifyInformById(inform);
    }
    
    /**
     * 发布通知公告
     * @param userNum
     * @param roleId
     * @param inform
     * @param session
     * @return
     */
    @RequestMapping(value = "/addInform.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addInform(@RequestParam("userNum") String userNum, @RequestParam("roleId") Integer roleId,
                                    Inform inform, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        inform.setPublisher(userNum);
        inform.setRoleId(roleId);
        inform.setSubject(userInfoService.findByUserNum(userNum).getData().getAcademy());
        
        return informService.addInform(inform);
    }
    
    /**
     * 批量删除通知公告
     * @param ids
     * @param roleId
     * @param userNum
     * @param session
     * @return
     */
    @RequestMapping("/batchRemoveInform.do")
    @ResponseBody
    public ServerResponse batchRemoveInform(@RequestParam("ids") Integer[] ids, @RequestParam("roleId") Integer roleId,
                                            @RequestParam("userNum") String userNum, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return informService.batchRemoveInform(ids, roleId);
    }
    
    /**
     * 根据userNum获取通知公告列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @param head
     * @param pubdate
     * @return
     */
    @RequestMapping(value = "/getInformListByAdminWithUserNum.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getInformListByAdminWithUserNum(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                          @RequestParam(value = "pageSize",defaultValue = "7") int pageSize,
                                                          HttpSession session, String head, String pubdate) {
        return informService.getInformListByAdmin(pageNum, pageSize, head, pubdate);
    }
    
    /**
     * 根据标题模糊查找通知公告
     * @param id
     * @return
     */
    @RequestMapping(value = "/goInformListWithHead.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse goInformListWithHead(Integer id) {
        return informService.getInformListByAdminWithHead(id);
    }
    
    /**
     * 通过id获取通知公告详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getInformDetailById.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getInformDetailById(Integer id) {
        return informService.getInformDetailById(id);
    }
    
}
