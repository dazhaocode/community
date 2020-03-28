package com.alan.community.exception;



/**
 * @author alan
 * @date 2020/3/21 15:58
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"问题不见了~，换一个试试？"),
    TARGET_NOT_SELECTED(2002,"未选中任何问题"),
    NO_LOGIN(2003,"你还没有登录~，请先登录"),
    SYS_ERROR(2004,"服务冒烟了，稍后试试~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论走丢了"),
    CONTENT_IS_EMPTY(2007,"评论内容为空!"),
    INVALID_TAG(2008,"输入非法标签"),
    NOTIFICATION_NOTFOUND(2009,"通知消失了~"),
    READ_NOTIFICATION_FAIL(3001,"读取失败~")
    ;

    private Integer code;
    private String msg;
    CustomizeErrorCode(Integer code,String msg){
         this.msg=msg;
         this.code=code;
     }


    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
