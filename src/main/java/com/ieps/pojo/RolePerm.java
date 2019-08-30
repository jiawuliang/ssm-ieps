package com.ieps.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RolePerm implements Serializable{

    private Integer id;

    // 角色Id
    private Integer roleId;

    // 权限Id
    private Integer permId;
    
    // 权限集合
    private List<Perm> permList;

    private Date createTime;

    private Date updateTime;
    
}
