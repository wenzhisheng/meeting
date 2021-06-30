package com.meeting.common.enums;

/**
 * @author: dameizi
 * @description: 使用状态
 * @dateTime 2019-03-29 20:26
 * @className com.meeting.common.enums.enableStatus
 */
public enum EnableStatus {

    /**
     * 未使用
     */
    UNUSE("0"),
    /**
     * 使用中
     */
    USEING("1");

    private String status;

    EnableStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

}
