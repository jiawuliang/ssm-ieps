package com.ieps.mapper;

import com.ieps.dto.ItemAdminDto;
import com.ieps.dto.UserAdminDto;
import com.ieps.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer itemId);
    
    int insert(Item record);
    
    int insertSelective(Item record);
    
    Item selectByPrimaryKey(Integer itemId);
    
    int updateByPrimaryKeySelective(Item record);
    
    int updateByPrimaryKey(Item record);
    
    
    // 学校管理员查看所有的项目
    List<ItemAdminDto> selectAllItem(@Param("itemAdminDto") ItemAdminDto itemAdminDto, @Param("list") List<Integer> list);
    
    // 学院管理员查看本学院所有的项目
    List<ItemAdminDto> selectAllItemByAdminWithUserNum(@Param("userNum") String userNum,
                                                       @Param("itemAdminDto") ItemAdminDto itemAdminDto,
                                                       @Param("list") List<Integer> list);
    
    // 校内专家 / 院内专家
    List<ItemAdminDto> selectAllItemByExportWithUserNum(@Param("userNum") String userNum,
                                                        @Param("itemAdminDto") ItemAdminDto itemAdminDto,
                                                        @Param("list") List<Integer> list);
    
    // 指导老师查看所指导的项目
    List<ItemAdminDto> selectAllItemByTutorWithUserNum(@Param("userNum") String userNum,
                                                       @Param("itemAdminDto") ItemAdminDto itemAdminDto,
                                                       @Param("list") List<Integer> list);
    
    // 普通学生查看自己的项目
    List<ItemAdminDto> selectAllItemByStuWithUserNum(@Param("userNum") String userNum,
                                                     @Param("itemAdminDto") ItemAdminDto itemAdminDto,
                                                     @Param("list") List<Integer> list);
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    // 学校管理员根据项目状态查看所有的项目
    List<ItemAdminDto> selectAllItemWithItemStatus(@Param("itemAdminDto") ItemAdminDto itemAdminDto);
    
    // 学院管理员查看本学院所有的项目
    List<ItemAdminDto> selectAllItemByAdminWithUserNumAndItemStatus(@Param("userNum") String userNum,
                                                                    @Param("itemAdminDto") ItemAdminDto itemAdminDto);
    
    
    // 校内专家 / 院内专家
    List<ItemAdminDto> selectAllItemByExportWithUserNumAndItemStatus(@Param("userNum") String userNum,
                                                                     @Param("itemAdminDto") ItemAdminDto itemAdminDto);
    
    
    // 指导老师查看所指导的项目
    List<ItemAdminDto> selectAllItemByTutorWithUserNumAndItemStatus(@Param("userNum") String userNum,
                                                                    @Param("itemAdminDto") ItemAdminDto itemAdminDto);
    
    
    // 普通学生查看自己的项目
    List<ItemAdminDto> selectAllItemByStuWithUserNumAndItemStatus(@Param("userNum") String userNum,
                                                                  @Param("itemAdminDto") ItemAdminDto itemAdminDto);
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    int deleteItemByItemNums(String[] itemNums);
    
    int updateItemStatusByItemNum(@Param("itemNum") String itemNum, @Param("itemStatus") int itemStatus);
    
    int updateItemByItemNum(ItemAdminDto itemAdminDto);
    
    ItemAdminDto selectItemByItemNum(String itemNum);
    
    int updateItemStatusWithItemNums(@Param("itemStatus") int itemStatus, @Param("itemNums") String[] itemNums);
    
    int insertItemWithItemAdminDto(ItemAdminDto itemAdminDto);
    
    List<ItemAdminDto> selectFinishedItemWithItemStatus(@Param("itemStatus") String itemStatus,
                                                        @Param("itemName") String itemName, @Param("itemDate") String itemDate);
    
    // 查看历届项目年份
    List<String> selectAllItemDate();
    
    ItemAdminDto selectItemDetailWithItemId(String itemNum);
    
    // 历届项目占比展示
    List<Item> selectItemsWithYear();
    
    // 展示控制管理
    List<UserAdminDto> selectStuUserInfoWithAcademy(@Param("itemDate") String itemDate);
    
    
}