package com.ieps.service.impl;

import com.ieps.common.ServerResponse;
import com.ieps.dto.UserAdminDto;
import com.ieps.mapper.ItemMapper;
import com.ieps.mapper.UserInfoMapper;
import com.ieps.pojo.Item;
import com.ieps.service.ShowAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class ShowAdminServiceImpl implements ShowAdminService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Autowired
    private ItemMapper itemMapper;
    
    
    @Override
    public ServerResponse getAcademyStuSex(String itemDate) {
        List<UserAdminDto> userAdminDtoList = itemMapper.selectStuUserInfoWithAcademy(itemDate);
    
        System.out.println(userAdminDtoList);
        if (userAdminDtoList.size() == 0) {
            return ServerResponse.createByErrorMessage("暂无数据，请检查数据！");
        }
        
        return ServerResponse.createBySuccess(userAdminDtoList);
    }
    
    @Override
    public ServerResponse getItemsWithYear() {
        
        List<Item> itemList = itemMapper.selectItemsWithYear();
        if (itemList.size() == 0) {
            return ServerResponse.createByErrorMessage("暂无数据，请检查数据！");
        }
    
        return ServerResponse.createBySuccess(itemList);
    }
}
