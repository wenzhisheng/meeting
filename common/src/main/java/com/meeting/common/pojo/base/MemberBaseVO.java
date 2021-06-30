package com.meeting.common.pojo.base;

import com.meeting.common.util.RedissonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dameizi
 * @description 会员基础信息
 * @dateTime 2019-06-06 23:19
 * @className MerchantBaseVO
 */
@ApiModel(value="会员基础信息",description="基础信息")
public class MemberBaseVO extends BaseVO {

    /** 当前登录人 */
    @ApiModelProperty(value="当前登录人", hidden=true)
    private String currentUser = RedissonUtil.getContextMerchantInfo() == null ? null : RedissonUtil.getContextMerchantInfo().getAccount();

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
