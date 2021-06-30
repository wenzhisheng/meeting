package com.meeting.common.pojo.permission.dto;

import com.meeting.common.pojo.admin.AdminVO;
import com.meeting.common.pojo.permission.AdminResourceVO;

import java.io.Serializable;
import java.util.List;

/**
 * @dateTime 2019-03-28 14:00
 * @author: dameizi
 * @description: 权限DTO
 */
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 6153874501592136340L;
    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 14:07
     * @description: 权限码集合
     * @param:
     * @return:
     */
    private List<String> permissionCode;
    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 14:09
     * @description: 菜单树
     * @param:
     * @return:
     */
    private List<AdminResourceVO> resourceTree;
    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 14:09
     * @description: 会员信息
     * @param:
     * @return:
     */
    private AdminVO adminVO;

    public List<String> getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(List<String> permissionCode) {
        this.permissionCode = permissionCode;
    }

    public List<AdminResourceVO> getResourceTree() {
        return resourceTree;
    }

    public void setResourceTree(List<AdminResourceVO> resourceTree) {
        this.resourceTree = resourceTree;
    }

    public AdminVO getAdminVO() {
        return adminVO;
    }

    public void setAdminVO(AdminVO adminVO) {
        this.adminVO = adminVO;
    }
}
