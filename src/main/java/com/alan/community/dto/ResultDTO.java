package com.alan.community.dto;

import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author alan
 * @date 2020/3/22 10:05
 */
@Data
public class ResultDTO {
    private String message;
    private Integer errorCode;

    public static ResultDTO errorOf(Integer errorCode,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(message);
        resultDTO.setErrorCode(errorCode);
        return  resultDTO;
    }
    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode){
        return errorOf(customizeErrorCode.getCode(),customizeErrorCode.getMsg());
    }
    public static ResultDTO successOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功！");
        resultDTO.setErrorCode(200);
        return  resultDTO;
    }
    public  static ResultDTO errorOf(CustomizeException e){
      return errorOf(e.getCode(),e.getMsg());
    }
}
