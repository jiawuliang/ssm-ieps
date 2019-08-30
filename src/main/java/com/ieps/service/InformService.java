package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.Inform;

/**
 * Created by ljw
 */
public interface InformService {

    ServerResponse getInformByUserNum(String userNum);
    
    ServerResponse getAllInformList(int pageNum, int pageSize, String userNum, Integer roleId);
    
    ServerResponse searchInformListWithCondition(int pageNum, int pageSize, String userNum, Integer roleId, Inform inform);
    
    ServerResponse removeInformById(Integer id, Integer roleId);
    
    ServerResponse modifyInformById(Inform inform);
    
    ServerResponse addInform(Inform inform);
    
    ServerResponse batchRemoveInform(Integer[] ids, Integer roleId);
    
    // 首页的重要通知公告列表
    ServerResponse getInformListByAdmin(int pageNum, int pageSize, String head, String pubdate);
    
    ServerResponse getInformListByAdminWithHead(Integer id);
    
    ServerResponse getInformDetailById(Integer id);
    
    
}
