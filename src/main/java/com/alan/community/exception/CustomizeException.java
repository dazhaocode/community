package com.alan.community.exception;

/**
 * @author alan
 * @date 2020/3/21 15:50
 */
public class CustomizeException extends RuntimeException {

    private  String msg;

   public  CustomizeException(ICustomizeErrorCode errorCode){
       this.msg=errorCode.getMsg();
   }

    public String getMsg() {
        return msg;
    }
}
