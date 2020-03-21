package com.alan.community.exception;

/**
 * @author alan
 * @date 2020/3/21 15:58
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("问题不见了~，换一个试试？");
    private String msg;

    CustomizeErrorCode(String msg){
         this.msg=msg;
     }


    @Override
    public String getMsg() {
        return msg;
    }
}
