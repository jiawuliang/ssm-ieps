package com.ieps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.dto.ItemAdminDto;
import com.ieps.mapper.*;
import com.ieps.pojo.Review;
import com.ieps.pojo.UserInfo;
import com.ieps.service.ItemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class ItemAdminServiceImpl implements ItemAdminService {
    
    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ItemInfoMapper itemInfoMapper;
    
    @Autowired
    private UserItemMapper userItemMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    @Autowired
    private FileHubMapper fileHubMapper;
    
    
    @Override
    public ServerResponse getItemListByUserNum(int pageNum, int pageSize, String userNumAdmin, int roleId,
                                               ItemAdminDto itemAdminDto, List<Integer> integerList) {
        PageHelper.startPage(pageNum, pageSize);
        
        List<ItemAdminDto> itemAdminDtoList = Lists.newArrayList();
        
        // 学校管理员
        if (Const.USERNUM_COLLEGE.equals(userNumAdmin)) {
            itemAdminDtoList = itemMapper.selectAllItem(itemAdminDto, integerList);
        }
        // 学院管理员 只能查看同一学院下的项目列表
        else if (Const.ROLEID_ACADEMY == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByAdminWithUserNum(userNumAdmin, itemAdminDto, integerList);
        }
        // 校内专家 /  院内专家
        else if (Const.ROLEID_COLLEGE_EXPERT == roleId || Const.ROLEID_ACADEMY_EXPERT == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByExportWithUserNum(userNumAdmin, itemAdminDto, integerList);
        }
        // 指导老师
        else if (Const.ROLEID_TUTOR == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByTutorWithUserNum(userNumAdmin, itemAdminDto, integerList);
        }
        // 普通学生
        else if (Const.ROLEID_STU == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByStuWithUserNum(userNumAdmin, itemAdminDto, integerList);
        }
        
        PageInfo pageInfo = new PageInfo(itemAdminDtoList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    // 根据项目状态获取项目
    @Override
    public ServerResponse getItemListByUserNumAndItemStatus(int pageNum, int pageSize, String userNumAdmin, int roleId, ItemAdminDto itemAdminDto) {
        PageHelper.startPage(pageNum, pageSize);
        
        List<ItemAdminDto> itemAdminDtoList = Lists.newArrayList();
        
        // 学校管理员
        if (Const.USERNUM_COLLEGE.equals(userNumAdmin)) {
            itemAdminDtoList = itemMapper.selectAllItemWithItemStatus(itemAdminDto);
        }
        // 学院管理员 只能查看同一学院下的项目列表
        else if (Const.ROLEID_ACADEMY == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByAdminWithUserNumAndItemStatus(userNumAdmin, itemAdminDto);
        }
        // 校内专家 / 院内专家
        else if (Const.ROLEID_COLLEGE_EXPERT == roleId || Const.ROLEID_ACADEMY_EXPERT == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByExportWithUserNumAndItemStatus(userNumAdmin, itemAdminDto);
        }
        // 指导老师
        else if (Const.ROLEID_TUTOR == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByTutorWithUserNumAndItemStatus(userNumAdmin, itemAdminDto);
        }
        // 普通学生
        else if (Const.ROLEID_STU == roleId) {
            itemAdminDtoList = itemMapper.selectAllItemByStuWithUserNumAndItemStatus(userNumAdmin, itemAdminDto);
        }
        
        PageInfo pageInfo = new PageInfo(itemAdminDtoList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse removeItemByItemNum(int roleId, String itemNum) {
        
        if (Const.ROLEID_ACADEMY == roleId || Const.ROLEID_COLLEGE == roleId) {
            
            // 或者自己构建一个数组，那么单个文件和批处理文件删除都调用同一个方法
            String[] itemNums = new String[1];
            
            itemNums[0] = itemNum;
            
            int result = itemMapper.deleteItemByItemNums(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除Item表中项目编号：" + itemNum + "失败");
            }
            
            result = itemInfoMapper.deleteItemInfoByItemNums(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除ItemInfo表中项目编号：" + itemNum + "失败");
            }
            
            result = userItemMapper.deleteUserItemByItemNums(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除UserItem表中项目编号：" + itemNum + "失败");
            }
            
            result = fileHubMapper.deleteFileHubByItemNum(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除FileHub表中项目编号：" + itemNum + "失败");
            }
            
            return ServerResponse.createBySuccessMessage("删除项目编号：" + itemNum + "及其关联表信息成功");
        }
        
        return ServerResponse.createByErrorMessage("对不起，目前你暂时没有权限执行删除操作！");
    }
    
    @Override
    public ServerResponse batchRemoveItemByItemNums(int roleId, String[] itemNums) {
        
        if (Const.ROLEID_ACADEMY == roleId || Const.ROLEID_COLLEGE == roleId) {
            int result = itemMapper.deleteItemByItemNums(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除Item表失败");
            }
            
            result = itemInfoMapper.deleteItemInfoByItemNums(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除ItemInfo表失败");
            }
            
            result = userItemMapper.deleteUserItemByItemNums(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除UserItem表失败");
            }
    
            result = fileHubMapper.deleteFileHubByItemNum(itemNums);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("删除FileHub表失败");
            }
            
        }
        return ServerResponse.createByErrorMessage("对不起，目前你暂时没有权限执行删除操作！");
    }
    
    @Override
    public ServerResponse modifyItemLevelByItemNum(int roleId, String itemNum, int itemLevel) {
        if (Const.ROLEID_COLLEGE != roleId) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限修改项目的级别！");
        }
        
        return ServerResponse.createBySuccess("恭喜你，修改项目编号：" + itemNum + "的级别成功！", itemInfoMapper.updateItemLevelByItemNum(itemNum, itemLevel));
    }
    
    @Override
    public ServerResponse modifyItemTypeByItemNum(int roleId, String itemNum, int itemType) {
        if (Const.ROLEID_COLLEGE != roleId && Const.ROLEID_ACADEMY != roleId) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限修改项目的类型！");
        }
        
        return ServerResponse.createBySuccess("恭喜你，修改项目编号：" + itemNum + "的类型成功！", itemInfoMapper.updateItemTypeByItemNum(itemNum, itemType));
    }
    
    @Override
    public ServerResponse modifyItemStatusByItemNum(int roleId, String itemNum, int itemStatus) {
        if (Const.ROLEID_COLLEGE != roleId && Const.ROLEID_ACADEMY != roleId) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限修改项目的状态！");
        }
        
        return ServerResponse.createBySuccess("恭喜你，修改项目编号：" + itemNum + "的状态成功！", itemMapper.updateItemStatusByItemNum(itemNum, itemStatus));
    }
    
    @Override
    public ServerResponse modifyItemByItemNum(int roleId, ItemAdminDto itemAdminDto) {
        
        int result = 0;
        
        ItemAdminDto itemAdminDtoTmp = itemMapper.selectItemByItemNum(itemAdminDto.getItemNum());
        if (itemAdminDtoTmp.getLeaderName() != itemAdminDto.getLeaderName() && itemAdminDtoTmp.getTutorName() != itemAdminDto.getTutorName()) {
            UserInfo userInfoLeader = userInfoMapper.selectUserInfoByUserName(itemAdminDto.getLeaderName());
            UserInfo userInfoTutor = userInfoMapper.selectUserInfoByUserName(itemAdminDto.getLeaderName());
            
            itemAdminDto.setLeaderNum(userInfoLeader.getUserNum());
            itemAdminDto.setTutorNum(userInfoTutor.getUserNum());
            
            result = itemMapper.updateItemByItemNum(itemAdminDto);
            
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，修改项目Item表信息错误！");
            }
            
            result = itemInfoMapper.updateItemByItemNum(itemAdminDto);
            
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，修改项目ItemInfo表信息错误！");
            }
            
            result = userItemMapper.updateUserNumByItemNumAndUserNum(itemAdminDto.getItemNum(), itemAdminDtoTmp.getLeaderNum());
            
            result = userItemMapper.updateUserNumByItemNumAndUserNum(itemAdminDto.getItemNum(), itemAdminDtoTmp.getTutorNum());
            
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，修改项目UserItem表信息错误！");
            }
        } else {
            
            result = itemInfoMapper.updateItemByItemNum(itemAdminDto);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，修改项目ItemInfo表信息错误！");
            }
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，修改项目信息成功！");
    }
    
    @Override
    public ServerResponse onekeyModifyItemTypeWithItemNums(int roleId, int itemType, String[] itemNums) {
        if (Const.ROLEID_COLLEGE != roleId && Const.ROLEID_ACADEMY != roleId) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限一键修改项目类型！");
        }
        
        if (itemInfoMapper.updateItemTypeWithItemNums(itemType, itemNums) <= 0) {
            return ServerResponse.createByErrorMessage("对不起，一键修改项目类型错误，请重试！");
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，一键修改项目类型成功！");
    }
    
    @Override
    public ServerResponse onekeyModifyItemStatusWithItemNums(int roleId, int itemStatus, String[] itemNums) {
        if (Const.ROLEID_COLLEGE != roleId && Const.ROLEID_ACADEMY != roleId) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限一键修改项目状态！");
        }
        
        if (itemMapper.updateItemStatusWithItemNums(itemStatus, itemNums) <= 0) {
            return ServerResponse.createByErrorMessage("对不起，一键修改项目状态错误，请重试！");
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，一键修改项目状态成功！");
    }
    
    @Override
    public ServerResponse onekeyModifyItemLevelWithItemNums(int roleId, int itemLevel, String[] itemNums) {
        if (Const.ROLEID_COLLEGE != roleId && Const.ROLEID_ACADEMY != roleId) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限一键修改项目级别！");
        }
        
        if (itemInfoMapper.updateItemLevelWithItemNums(itemLevel, itemNums) <= 0) {
            return ServerResponse.createByErrorMessage("对不起，一键修改项目级别错误，请重试！");
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，一键修改项目级别成功！");
    }
    
    @Override
    public ServerResponse addItemAndUserInfo(ItemAdminDto itemAdminDto, String[] userNums, String[] userNams) {
        
        // 手动计算itemNum  取年月 + 201905
        // todo 查找数据库最大值+1
        
        itemAdminDto.setItemStatus(1); //申请中
        
        int result = itemMapper.insertItemWithItemAdminDto(itemAdminDto);
        if (result <= 0) {
            return ServerResponse.createByErrorMessage("申请表Item插入失败");
        }
        
        result = itemInfoMapper.insertItemInfoWithSummary(itemAdminDto.getItemNum(), itemAdminDto.getItemType(), itemAdminDto.getSummary());
        if (result <= 0) {
            return ServerResponse.createByErrorMessage("申请表ItemInfo插入失败");
        }
        
        // 1：成员/2：负责人/3：指导老师/4：院内评委/5：院内评委组长/6：校内评委/7：校内评委组长
        result = userItemMapper.insertUserItemWithItemNumAndUserNum(itemAdminDto.getLeaderNum(), itemAdminDto.getItemNum(), 2);
        
        if (result <= 0) {
            return ServerResponse.createByErrorMessage("申请表UserItem插入失败");
        }
        
        result = userItemMapper.insertUserItemWithItemNumAndUserNum(itemAdminDto.getTutorNum(), itemAdminDto.getItemNum(), 3);
        for (int i = 0; i < userNums.length; i++) {
            result = userItemMapper.insertUserItemWithItemNumAndUserNum(userNums[i], itemAdminDto.getItemNum(), 1);
        }
        
        if (result <= 0) {
            return ServerResponse.createByErrorMessage("申请表UserItem插入失败");
        }
        
        return ServerResponse.createBySuccessMessage("插入申请表信息成功！");
    }
    
    @Override
    public ServerResponse getReviewLeaderByRoleIdAndUserNum(String userNum, int roleId) {
        if (roleId == Const.ROLEID_ACADEMY || roleId == Const.ROLEID_COLLEGE) {
            UserInfo userInfo = userInfoMapper.selectByUserNum(userNum);
            if (userInfo == null) {
                return ServerResponse.createByErrorMessage("对不起，可能网络出错了，暂时无法找到你的信息！");
            }
            List<UserInfo> userInfoList = userInfoMapper.selectUserInfoByAcademy(userInfo.getAcademy());
            if (userInfoList.size() == 0) {
                return ServerResponse.createByErrorMessage("对不起，你所在单位没有院评审组，请先创建评审组！");
            }
            
            return ServerResponse.createBySuccess(userInfoList);
        }
        
        return ServerResponse.createByErrorMessage("对不起，目前你还没有权限指派评审！");
    }
    
    @Override
    public ServerResponse addUserItemWithLeader(int roleId, String userNum, String userName, String itemNum, Integer itemStatus) {
        if (roleId == Const.ROLEID_ACADEMY) {
            String userNameTemp = userName.substring(0, userName.lastIndexOf("组员"));
            System.out.println(userNameTemp);
            List<UserInfo> userInfoList = userInfoMapper.selectUserInfoLikeUserName(userNum, userNameTemp);
            
            if (userInfoList == null) {
                return ServerResponse.createByErrorMessage("对不起，可能网络出错了，暂时无法找到你的信息！");
            }
            //// 1：成员/2：负责人/3：指导老师/4：院内评委/5：院内评委组长/6：校内评委/7：校内评委组长
            
            int result = userItemMapper.insertUserItemWithItemNumAndUserNum(userNum, itemNum, 5);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，选择学院评审组长数据错误！");
            }
            
            for (UserInfo userInfo : userInfoList) {
                result = userItemMapper.insertUserItemWithItemNumAndUserNum(userInfo.getUserNum(), itemNum, 4);
            }
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，选择学院评审委员数据错误！");
            }
            
            result = itemMapper.updateItemStatusByItemNum(itemNum, itemStatus);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，更新项目状态数据错误！");
            }
            
        }
        else if (roleId == Const.ROLEID_COLLEGE) {
            String userNameTemp = userName.substring(0, userName.lastIndexOf("组员"));
            System.out.println(userNameTemp);
            List<UserInfo> userInfoList = userInfoMapper.selectUserInfoLikeUserName(userNum, userNameTemp);
            if (userInfoList == null) {
                return ServerResponse.createByErrorMessage("对不起，可能网络出错了，暂时无法找到你的信息！");
            }
            //// 1：成员/2：负责人/3：指导老师/4：院内评委/5：院内评委组长/6：校内评委/7：校内评委组长
            
            int result = userItemMapper.insertUserItemWithItemNumAndUserNum(userNum, itemNum, 7);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，选择学校评审组长数据错误！");
            }
            
            for (UserInfo userInfo : userInfoList) {
                result = userItemMapper.insertUserItemWithItemNumAndUserNum(userInfo.getUserNum(), itemNum, 6);
            }
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("对不起，选择学校评审委员数据错误！");
            }
        }
        
        return ServerResponse.createBySuccessMessage("恭喜您，选择评审组长及其组员成功！");
    }
    
    @Override
    public ServerResponse batchAddReviewTeam(int roleId, String userNum, String userName, String[] itemNums, int itemStatus) {
        
        if (roleId == Const.ROLEID_ACADEMY) {
            String userNameTemp = userName.substring(0, userName.lastIndexOf("组员"));
            System.out.println(userNameTemp);
            List<UserInfo> userInfoList = userInfoMapper.selectUserInfoLikeUserName(userNum, userNameTemp);
        
            if (userInfoList == null) {
                return ServerResponse.createByErrorMessage("对不起，可能网络出错了，暂时无法找到你的信息！");
            }
            //// 1：成员/2：负责人/3：指导老师/4：院内评委/5：院内评委组长/6：校内评委/7：校内评委组长
        
            for (int i = 0; i < itemNums.length; i++) {
                int result = userItemMapper.insertUserItemWithItemNumAndUserNum(userNum, itemNums[i], 5);
                if (result <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，选择学院评审组长数据错误！");
                }
    
                for (UserInfo userInfo : userInfoList) {
                    result = userItemMapper.insertUserItemWithItemNumAndUserNum(userInfo.getUserNum(), itemNums[i], 4);
                }
                if (result <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，选择学院评审委员数据错误！");
                }
    
                result = itemMapper.updateItemStatusByItemNum(itemNums[i], itemStatus);
                if (result <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，更新项目状态数据错误！");
                }
            }
            
        }
        else if (roleId == Const.ROLEID_COLLEGE) {
            String userNameTemp = userName.substring(0, userName.lastIndexOf("组员"));
            System.out.println(userNameTemp);
            List<UserInfo> userInfoList = userInfoMapper.selectUserInfoLikeUserName(userNum, userNameTemp);
            if (userInfoList == null) {
                return ServerResponse.createByErrorMessage("对不起，可能网络出错了，暂时无法找到你的信息！");
            }
            //// 1：成员/2：负责人/3：指导老师/4：院内评委/5：院内评委组长/6：校内评委/7：校内评委组长
    
            for (int i = 0; i < itemNums.length; i++) {
                int result = userItemMapper.insertUserItemWithItemNumAndUserNum(userNum, itemNums[i], 7);
                if (result <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，选择学校评审组长数据错误！");
                }
    
                for (UserInfo userInfo : userInfoList) {
                    result = userItemMapper.insertUserItemWithItemNumAndUserNum(userInfo.getUserNum(), itemNums[i], 6);
                }
                if (result <= 0) {
                    return ServerResponse.createByErrorMessage("对不起，选择学校评审委员数据错误！");
                }
            }
            
        }
    
        return ServerResponse.createBySuccessMessage("恭喜您，批量指派评审组长及其组员成功！");
    }
    
    @Override
    public ServerResponse addReviewByExport(int roleId, Review review) {
        if (Const.ROLEID_ACADEMY_EXPERT == roleId || Const.ROLEID_COLLEGE_EXPERT == roleId) {
            if (reviewMapper.insert(review) <= 0) {
                return ServerResponse.createByErrorMessage("对不起，插入评审结果失败，请重新填写！");
            }
            
            return ServerResponse.createBySuccessMessage("恭喜你，插入评审结果成功，请继续操作！");
        }
        
        return ServerResponse.createByErrorMessage("对不起，目前你没有权限进行评审操作！");
    }
    
    @Override
    public ServerResponse getFinishedItem(int pageNum, int pageSize, String itemName, String itemDate) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        
        // List<ItemAdminDto> itemAdminDtoList = itemMapper.selectFinishedItemWithItemStatus(Const.FINISH_ITEMSTATUS, itemName, itemDate);
        List<ItemAdminDto> itemAdminDtoList = itemMapper.selectFinishedItemWithItemStatus("1", itemName, itemDate);
        if (itemAdminDtoList.size() == 0) {
            return ServerResponse.createByErrorMessage("暂无数据！");
        }
    
        // 设置分页
        PageInfo pageInfo = new PageInfo(itemAdminDtoList);
    
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse checkIsReviewWithItemNum(String itemNum, String userNum, int reviewLevel) {
        int result = userItemMapper.selectUserItemWithItemNumAndUserNum(itemNum, userNum, reviewLevel);
        if (result <= 0) {
            return ServerResponse.createBySuccess();
        }
        
        return ServerResponse.createByErrorMessage("对不起，不能再重复评审同状态的同一项目，请及时上传评审结果！");
    }
    
    @Override
    public ServerResponse getItemStatusWithItemNum(String itemNum) {
        ItemAdminDto itemAdminDto = itemMapper.selectItemByItemNum(itemNum);
        if (itemAdminDto == null) {
            return ServerResponse.createByErrorMessage("对不起哦，该项目不能被修改状态！");
        }
        
        return ServerResponse.createBySuccess(itemAdminDto);
    }
    
    @Override
    public ServerResponse getItemDate() {
        List<String> itemDateList = itemMapper.selectAllItemDate();
        
        if (itemDateList.size() == 0) {
            return ServerResponse.createByError();
        }
        
        return ServerResponse.createBySuccess(itemDateList);
    }
    
    @Override
    public ServerResponse getItemDetailWithItemId(String itemNum) {
        ItemAdminDto itemAdminDto = itemMapper.selectItemDetailWithItemId(itemNum);
        
        return ServerResponse.createBySuccess(itemAdminDto);
    }
    
    @Override
    public ServerResponse removeUserItemWithReview(String itemNum, int roleId) {
        if (roleId != Const.ROLEID_COLLEGE && roleId != Const.ROLEID_ACADEMY) {
            return ServerResponse.createByErrorMessage("对不起，身份验证失败！");
        }
        
        int result = userItemMapper.deleteUsetItemWithReviews(itemNum);
        if (result <= 0) {
            return ServerResponse.createByError();
        }
        
        return ServerResponse.createBySuccess();
    }
    
    
}
