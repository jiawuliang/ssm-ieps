package com.ieps.mapper;

import com.ieps.dto.ItemAdminDto;
import com.ieps.pojo.ItemInfo;
import org.apache.ibatis.annotations.Param;

public interface ItemInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemInfo record);

    int insertSelective(ItemInfo record);

    ItemInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemInfo record);

    int updateByPrimaryKey(ItemInfo record);
    
    
    
    
    int deleteItemInfoByItemNums(String[] itemNums);
    
    int updateItemLevelByItemNum(@Param("itemNum") String itemNum, @Param("itemLevel") int itemLevel);
    
    int updateItemTypeByItemNum(@Param("itemNum") String itemNum, @Param("itemType") int itemType);
    
    int updateItemByItemNum(ItemAdminDto itemAdminDto);
    
    
    int updateItemTypeWithItemNums(@Param("itemType") int itemType, @Param("itemNums") String[] itemNums);
    
    int updateItemLevelWithItemNums(@Param("itemLevel") int itemLevel, @Param("itemNums") String[] itemNums);
    
    int insertItemInfoWithSummary(@Param("itemNum") String itemNum, @Param("itemType") Integer itemType, @Param("summary") String summary);
}