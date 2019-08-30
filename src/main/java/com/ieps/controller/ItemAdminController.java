package com.ieps.controller;

import com.google.common.collect.Lists;
import com.ieps.common.ServerResponse;
import com.ieps.dto.ItemAdminDto;
import com.ieps.pojo.Review;
import com.ieps.pojo.User;
import com.ieps.service.ItemAdminService;
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
public class ItemAdminController {
    
    @Autowired
    private ItemAdminService itemAdminService;
    
    /**
     * 根据userNum获取项目，分页显示
     * @param page
     * @param itemAdminDto
     * @param limit
     * @param session
     * @param userNumAdmin
     * @param roleId
     * @param itemStatusF
     * @param itemStatusS
     * @param itemStatusT
     * @param itemStatusFF
     * @return
     */
    @RequestMapping("/getItemListByUserNum.do")
    @ResponseBody
    public ServerResponse getItemListByUserNum(@RequestParam(value = "page", defaultValue = "1") int page, ItemAdminDto itemAdminDto,
                                               @RequestParam(value = "limit", defaultValue = "10") int limit, HttpSession session,
                                               @RequestParam("userNumAdmin") String userNumAdmin, @RequestParam("roleId") int roleId,
                                               @RequestParam(value = "itemStatusF", required = false) Integer itemStatusF,
                                               @RequestParam(value = "itemStatusS", required = false) Integer itemStatusS,
                                               @RequestParam(value = "itemStatusT", required = false) Integer itemStatusT,
                                               @RequestParam(value = "itemStatusFF", required = false) Integer itemStatusFF) {
       
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        // <!-- 项目状态：1：申请中；2：立项评审；3：已立项；4：立项失败；5：中期检查; 6: 待结题；7：结题评审；8：结题成功；9：结题失败-->
    
        List<Integer> integerList = Lists.newArrayList();
        if (itemStatusF != null) {
            integerList.add(itemStatusF);
        }
        if (itemStatusS != null) {
            integerList.add(itemStatusS);
        }
        if (itemStatusT != null) {
            integerList.add(itemStatusT);
        }
        if (itemStatusFF != null) {
            integerList.add(itemStatusFF);
        }
        
        return itemAdminService.getItemListByUserNum(page, limit, userNumAdmin, roleId, itemAdminDto, integerList);
    }
    
    /**
     * 根据userNum和itemStatus获取项目列表
     * @param page
     * @param itemAdminDto
     * @param limit
     * @param session
     * @param userNumAdmin
     * @param roleId
     * @return
     */
    @RequestMapping("/getItemListByUserNumAndItemStatus.do")
    @ResponseBody
    public ServerResponse getItemListByUserNumAndItemStatus(@RequestParam(value = "page", defaultValue = "1") int page, ItemAdminDto itemAdminDto,
                                               @RequestParam(value = "limit", defaultValue = "5") int limit, HttpSession session,
                                               @RequestParam("userNumAdmin") String userNumAdmin, @RequestParam("roleId") int roleId) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.getItemListByUserNumAndItemStatus(page, limit, userNumAdmin, roleId, itemAdminDto);
    }
    
    /**
     * 根据项目编号删除项目
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNum
     * @return
     */
    @RequestMapping("/removeItemById.do")
    @ResponseBody
    public ServerResponse removeItemById(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                         HttpSession session, String itemNum) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.removeItemByItemNum(roleId, itemNum);
    }
    
    /**
     * 根据项目编号组，批量删除项目
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNums
     * @return
     */
    @RequestMapping("/batchRemoveItem.do")
    @ResponseBody
    public ServerResponse batchRemoveItem(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                          HttpSession session, String[] itemNums) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.batchRemoveItemByItemNums(roleId, itemNums);
    }
    
    /**
     * 根据项目编号 修改项目级别
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNum
     * @param itemlevel
     * @return
     */
    @RequestMapping("/modifyItemLevelByItemNum.do")
    @ResponseBody
    public ServerResponse modifyItemLevelByItemNum(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                          HttpSession session, String itemNum, @RequestParam("itemLevel") int itemlevel) {
        
        User user = (User) session.getAttribute("activeUser");
    
        System.out.println(itemlevel);
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.modifyItemLevelByItemNum(roleId, itemNum, itemlevel);
    }
    
    /**
     * 根据项目编号 修改项目类型
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNum
     * @param itemType
     * @return
     */
    @RequestMapping("/modifyItemTypeByItemNum.do")
    @ResponseBody
    public ServerResponse modifyItemTypeByItemNum(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                   HttpSession session, String itemNum, @RequestParam("itemType") int itemType) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.modifyItemTypeByItemNum(roleId, itemNum, itemType);
    }
    
    /**
     * 根据项目编号 修改项目状态
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNum
     * @param itemStatus
     * @return
     */
    @RequestMapping("/modifyItemStatusByItemNum.do")
    @ResponseBody
    public ServerResponse modifyItemStatusByItemNum(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                  HttpSession session, String itemNum, @RequestParam("itemStatus") int itemStatus) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.modifyItemStatusByItemNum(roleId, itemNum, itemStatus);
    }
    
    /**
     * 根据项目编号 修改项目信息
     * @param userNum
     * @param roleId
     * @param session
     * @param itemAdminDto
     * @return
     */
    @RequestMapping("/modifyItemByItemNum.do")
    @ResponseBody
    public ServerResponse modifyItemByItemNum(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                    HttpSession session, ItemAdminDto itemAdminDto) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.modifyItemByItemNum(roleId, itemAdminDto);
    }
    
    /**
     * 根据项目编号组一键修改项目类型
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNums
     * @param itemType
     * @return
     */
    @RequestMapping("/onekeyModifyItemTypeWithItemNums.do")
    @ResponseBody
    public ServerResponse onekeyModifyItemTypeWithItemNums(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                              HttpSession session, String[] itemNums, int itemType) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.onekeyModifyItemTypeWithItemNums(roleId, itemType, itemNums);
    }
    
    /**
     * 根据项目编号组一键修改项目状态
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNums
     * @param itemStatus
     * @return
     */
    @RequestMapping("/onekeyModifyItemStatusWithItemNums.do")
    @ResponseBody
    public ServerResponse onekeyModifyItemStatusWithItemNums(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                           HttpSession session, String[] itemNums, Integer itemStatus) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.onekeyModifyItemStatusWithItemNums(roleId, itemStatus, itemNums);
    }
    
    /**
     * 根据项目编号组一键修改项目级别
     * @param userNum
     * @param roleId
     * @param session
     * @param itemNums
     * @param itemLevel
     * @return
     */
    @RequestMapping("/onekeyModifyItemLevelWithItemNums.do")
    @ResponseBody
    public ServerResponse onekeyModifyItemLevelWithItemNums(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                                           HttpSession session, String[] itemNums, int itemLevel) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.onekeyModifyItemLevelWithItemNums(roleId, itemLevel, itemNums);
    }
    
    /**
     * 添加项目和用户信息   立即申请项目
     * @param userNum
     * @param roleId
     * @param session
     * @param userNums
     * @param userNames
     * @param itemAdminDto
     * @return
     */
    @RequestMapping("/addItemAndUserInfo.do")
    @ResponseBody
    public ServerResponse addItemAndUserInfo(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId, HttpSession session,
                                             @RequestParam("userNums") String[] userNums, @RequestParam("userNames") String[] userNames, ItemAdminDto itemAdminDto) {
    
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.addItemAndUserInfo(itemAdminDto, userNums, userNames);
    }
    
    /**
     * 根据roleId和userNum获取评审小组组员
     * @param userNum
     * @param roleId
     * @param session
     * @return
     */
    @RequestMapping("/getReviewLeaderByRoleIdAndUserNum.do")
    @ResponseBody
    public ServerResponse getReviewLeaderByRoleIdAndUserNum(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId, HttpSession session) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.getReviewLeaderByRoleIdAndUserNum(userNum, roleId);
    }
    
    /**
     * 指派评委
     * @param userNumAdmin
     * @param roleId
     * @param userNum
     * @param userName
     * @param itemNum
     * @param itemStatus
     * @param session
     * @return
     */
    @RequestMapping("/chooseUserItemWithLeader.do")
    @ResponseBody
    public ServerResponse chooseUserItemWithLeader(@RequestParam("userNumAdmin") String userNumAdmin, @RequestParam("roleId") int roleId,
                                                   @RequestParam("userNum") String userNum, @RequestParam("userName") String userName,
                                                   @RequestParam("itemNum") String itemNum, @RequestParam("itemStatus") int itemStatus,
                                                   HttpSession session) {
        
        
        User user = (User) session.getAttribute("activeUser");
        
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.addUserItemWithLeader(roleId, userNum, userName, itemNum, itemStatus);
    }
    
    
    @RequestMapping(value = "/batchAddReviewTeam.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse batchAddReviewTeam(@RequestParam("userNumAdmin") String userNumAdmin, @RequestParam("roleId") int roleId,
                                             @RequestParam("userNum") String userNum, @RequestParam("userName") String userName,
                                             @RequestParam("itemNums") String[] itemNums, @RequestParam("itemStatus") int itemStatus,
                                             HttpSession session) {
    
        User user = (User) session.getAttribute("activeUser");
    
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
    
        return itemAdminService.batchAddReviewTeam(roleId, userNum, userName, itemNums, itemStatus);
    }
    
    /**
     * 专家提交评审结果
     * @param roleId
     * @param review
     * @param session
     * @return
     */
    @RequestMapping("/addReviewByExport.do")
    @ResponseBody
    public ServerResponse addReviewByExport(@RequestParam("roleId") int roleId, Review review, HttpSession session) {
        
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return itemAdminService.addReviewByExport(roleId, review);
    }
    
    // 首页中的项目展览
    
    /**
     * 首页中历届项目展示，已完成的
     * @param pageNum
     * @param pageSize
     * @param session
     * @param itemName
     * @param itemDate
     * @return
     */
    @RequestMapping("/getFinishedItemList.do")
    @ResponseBody
    public ServerResponse getFinishedItemList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                              HttpSession session, String itemName, String itemDate) {
        return itemAdminService.getFinishedItem(pageNum, pageSize, itemName, itemDate);
    }
    
    /**
     * 根据ItemNum和userNum检查项目是否评审
     * @param itemNum
     * @param userNum
     * @return
     */
    @RequestMapping("/checkIsReviewWithItemNumAndUserNum.do")
    @ResponseBody
    public ServerResponse checkIsReviewWithItemNumAndUserNum(String itemNum, String userNum, int reviewLevel) {
        return itemAdminService.checkIsReviewWithItemNum(itemNum, userNum, reviewLevel);
    }
    
    /**
     * 根据itemNum获取项目状态
     * @param itemNum
     * @return
     */
    @RequestMapping("/getItemStatusWithItemNum.do")
    @ResponseBody
    public ServerResponse getItemStatusWithItemNum(String itemNum) {
        return itemAdminService.getItemStatusWithItemNum(itemNum);
    }
    
    /**
     * 获取历届项目年份
     * @return
     */
    @RequestMapping("/getItemDate.do")
    @ResponseBody
    public ServerResponse getItemDate() {
        return itemAdminService.getItemDate();
    }
    
    /**
     * 根据itemNum获取项目详情
     * @param itemNum
     * @return
     */
    @RequestMapping("/getItemDetailWithItemNum.do")
    @ResponseBody
    public ServerResponse getItemDetailWithItemNum(String itemNum) {
        return itemAdminService.getItemDetailWithItemId(itemNum);
    }
    
    
    @RequestMapping("/removeUserItemWithReview.do")
    @ResponseBody
    public ServerResponse removeUserItemWithReview(String itemNum, int roleId) {
    
        return itemAdminService.removeUserItemWithReview(itemNum, roleId);
    }
    
    
    
    
    
    
    
    
    
    
}
