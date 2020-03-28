package com.alan.community.dto;

import lombok.Data;

/**
 * @author alan
 * @date 2020/3/27 21:12
 */
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private  String typeName;
    private Integer type;
    private Long outerid;
}
