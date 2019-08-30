package com.ieps.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Item implements Serializable {

    private Integer itemId;

    // 项目编号：年+月+6位数字   2018+10+595036
    private String itemNum;

    // 项目名称
    private String itemName;
    
    // 项目负责人学号
    private String leaderNum;

    // 项目负责人姓名
    private String leaderName;
    
    // 指导老师教师号
    private String tutorNum;

    // 指导老师姓名
    private String tutorName;

    // 项目状态：1：申请中；2：立项评审；3：已立项；4：立项失败；5：中期检查; 6: 待结题；7：结题评审；8：结题成功；9：结题失败
    private Integer itemStatus;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date itemDate;

    private Date createTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

}
