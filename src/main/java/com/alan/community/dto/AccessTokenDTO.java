package com.alan.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/3/17 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDTO {
    private String grant_type;
    private String code;
    private String client_id;
    private String redirect_uri;
    private String client_secret;

}
