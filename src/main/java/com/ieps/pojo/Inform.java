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
public class Inform implements Serializable {
    
    private Integer id;
    
    // 标题
    private String head;
    
    // 发布者  userNum
    private String publisher;
    
    // 发布角色
    private Integer roleId;
    
    // 发布主体
    private String subject;
    
    // 内容
    private String content;
    
    // 文件链接
    private String files;
    
    // @DatetimeFormat是将String转换成Date，一般前台给后台传值时用
    // @JsonFormat(pattern="yyyy-MM-dd") 将Date转换成String 一般后台传值给前台时
    
    // 发布时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pubdate;
    
    private Date createTime;
    
    private Date updateTime;
    
}
