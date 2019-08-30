package com.ieps.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by ljw
 */
@Data
@Accessors(chain = true)
public class Role implements Serializable {

    private Integer roleId;

    // 角色名
    private String roleName;

    // 角色描述
    private String roleDesc;

    private Date createTime;

    private Date updateTime;
    
    // 权限集合
    private Set<Perm> permList;
    
}
