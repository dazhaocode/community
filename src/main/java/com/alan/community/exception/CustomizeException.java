package com.alan.community.exception;

/**
 * @author alan
 * @date 2020/3/21 15:50
 */
public class CustomizeException extends RuntimeException {

    private  String msg;
    private  Integer code;

   public  CustomizeException(ICustomizeErrorCode errorCode){
       this.code=errorCode.getCode();
       this.msg=errorCode.getMsg();
   }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
