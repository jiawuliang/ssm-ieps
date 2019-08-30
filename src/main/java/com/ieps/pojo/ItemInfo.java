package com.ieps.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ItemInfo implements Serializable {

    private Integer id;

    // 项目编号
    private String itemNum;
    
    // 项目级别：-1: 立项失败；0：校级；1：省区级；2：国家级
    private Integer itemLevel;

    // 项目类型：0：创新训练；1：创业训练；2：创业实践
    private Integer itemType;

    // 项目简介，少于200个字
    private String summary;

    // 校级经费
    private BigDecimal collegeFunds;

    // 财政经费
    private BigDecimal governFunds;

    private Date createTime;

    private Date updateTime;

}
