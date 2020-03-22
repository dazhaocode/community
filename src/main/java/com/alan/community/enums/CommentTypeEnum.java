package com.alan.community.enums;

/**
 * @author alan
 * @date 2020/3/22 10:13
 */
public enum  CommentTypeEnum {
        QUESTION(1L),
        COMMENT(2L)
    ;

    private Long type;

    CommentTypeEnum(Long type) {
        this.type = type;
    }

    public static boolean isExist(Long type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType()==type) {
                return  true;
            }
        }
        return false;
    }

    public Long getType() {
        return type;
    }
}
