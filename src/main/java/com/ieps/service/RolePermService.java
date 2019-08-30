package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.RolePerm;

import java.util.List;

/**
 * Created by ljw
 */
public interface RolePermService {

    ServerResponse<List<RolePerm>> getMenu(Integer roleId);
}
