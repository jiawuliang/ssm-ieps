package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.dto.ItemAdminDto;
import com.ieps.pojo.Review;

import java.util.List;

/**
 * Created by ljw
 */
public interface ItemAdminService {
    
    ServerResponse getItemListByUserNum(int pageNum, int pageSize, String userNumAdmin, int roleId,
                                        ItemAdminDto itemAdminDto, List<Integer> integerList);
    
    ServerResponse getItemListByUserNumAndItemStatus(int pageNum, int pageSize, String userNumAdmin, int roleId, ItemAdminDto itemAdminDto);
    
    ServerResponse removeItemByItemNum(int roleId, String itemNum);
    
    ServerResponse batchRemoveItemByItemNums(int roleId, String[] itemNums);
    
    ServerResponse modifyItemLevelByItemNum(int roleId, String itemNum, int itemLevel);
    
    ServerResponse modifyItemTypeByItemNum(int roleId, String itemNum, int itemType);
    
    ServerResponse modifyItemStatusByItemNum(int roleId, String itemNum, int itemStatus);
    
    ServerResponse modifyItemByItemNum(int roleId, ItemAdminDto itemAdminDto);
    
    ServerResponse onekeyModifyItemTypeWithItemNums(int roleId, int itemType, String[] itemNums);
    
    ServerResponse onekeyModifyItemLevelWithItemNums(int roleId, int itemLevel, String[] itemNums);
    
    ServerResponse onekeyModifyItemStatusWithItemNums(int roleId, int itemStatus, String[] itemNums);
    
    ServerResponse addItemAndUserInfo(ItemAdminDto itemAdminDto, String[] userNums, String[] userNams);
    
    ServerResponse getReviewLeaderByRoleIdAndUserNum(String userNum, int roleId);
    
    ServerResponse addUserItemWithLeader(int roleId, String userNum, String userName, String itemNum, Integer itemStatus);
    
    ServerResponse batchAddReviewTeam(int roleId, String userNum, String userName, String[] itemNums, int itemStatus);
   
    ServerResponse addReviewByExport(int roleId, Review review);
    
    ServerResponse getFinishedItem(int pageNum, int pageSize, String itemName, String itemDate);
    
    ServerResponse checkIsReviewWithItemNum(String itemNum, String userNum, int reviewLevel);
    
    ServerResponse getItemStatusWithItemNum(String itemNum);
    
    ServerResponse getItemDate();
    
    ServerResponse getItemDetailWithItemId(String itemNum);
    
    // 删除userItem之前的评委关联信息
    ServerResponse removeUserItemWithReview(String itemNum, int roleId);
    
}
