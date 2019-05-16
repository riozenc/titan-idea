package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Table(name = "ROLE_MENU_REL")
public class RoleMenuRel implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "MENU_ID")
    private Integer menuId;

    @Column(name = "BUTTON")
    private Integer button;

    @Column(name = "OPERATOR_ID")
    private Integer operatorId;

    @Column(name = "STATUS")
    private Integer status;

    private Integer count;

    private static final long serialVersionUID = 1L;

    @Transient
    private List<String> accessList;

    @Transient
    private List<Map<String,String>> buttonData;

    @Transient
    private List<Integer> oldRoleList;

    @Transient
    private List<Integer> newRoleList;

    @Transient
    private List<Integer> needDelList;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return ROLE_ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return MODULE_ID
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getButton() {
        return button;
    }

    public void setButton(Integer button) {
        this.button = button;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<String> accessList) {
        this.accessList = accessList;
    }

    public List<Map<String, String>> getButtonData() {
        return buttonData;
    }

    public void setButtonData(List<Map<String, String>> buttonData) {
        this.buttonData = buttonData;
    }

    public List<Integer> getOldRoleList() {
        return oldRoleList;
    }

    public void setOldRoleList(List<Integer> oldRoleList) {
        this.oldRoleList = oldRoleList;
    }

    public List<Integer> getNewRoleList() {
        return newRoleList;
    }

    public void setNewRoleList(List<Integer> newRoleList) {
        this.newRoleList = newRoleList;
    }

    public List<Integer> getNeedDelList() {
        return needDelList;
    }

    public void setNeedDelList(List<Integer> needDelList) {
        this.needDelList = needDelList;
    }
}