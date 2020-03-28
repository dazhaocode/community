package com.alan.community.enums;

public enum NotificationEnum {

    UNREAD(0),READ(1);

    private int status;

    NotificationEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
