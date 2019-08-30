package com.ieps.service;

import com.ieps.common.ServerResponse;

/**
 * Created by ljw
 */
public interface ShowAdminService {
    
    ServerResponse getAcademyStuSex(String itemDate);
    
    ServerResponse getItemsWithYear();


}
