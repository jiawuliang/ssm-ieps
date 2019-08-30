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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserItem implements Serializable {

    private Integer id;

    // 用户userNum
    private String userNum;

    // 项目编号
    private String itemNum;

    // 身份标识
    // 1：成员/2：负责人/3：指导老师/4：院内评委/5：院内评委组长/6：校内评委/7：校内评委组长
    private Integer identify;

    private Date createTime;

    private Date updateTime;
    
}
