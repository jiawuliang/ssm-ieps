package com.ieps.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ljw
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Perm implements Serializable {

    private Integer permId;

    // 权限名
    private String permName;
    
    // 权限类型：0：menu；1：permission
    private String permType;

    // url
    private String url;

    // 图标
    private String icon;
    
    // 父菜单id
    private Integer parentId;

    // 具体权限
    private String permCode;

    // 权限描述
    private String permDesc;

    private Date createTime;

    private Date updateTime;

    // private List<Role> roleList;
    
}
