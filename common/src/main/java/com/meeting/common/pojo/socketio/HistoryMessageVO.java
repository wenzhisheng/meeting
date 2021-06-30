package com.meeting.common.pojo.socketio;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-06-14 14:43
 * @className com.meeting.common.pojo.socketio.ChatMessage
 */
public class HistoryMessageVO implements Serializable {

    private static final long serialVersionUID = -8791127660006379017L;

    /** 发送人ID */
    private Integer sendId;
    /** 接收人ID */
    private Integer receiveId;
    /** 页码 */
    private int pageNo = 1;
    /** 每页条数 */
    private int pageSize = 30;

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
