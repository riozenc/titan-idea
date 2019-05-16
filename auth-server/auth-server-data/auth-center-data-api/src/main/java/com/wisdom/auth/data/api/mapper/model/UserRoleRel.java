package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "USER_ROLE_REL")
public class UserRoleRel implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "ROLE_ID")
    private Integer roleId;


    private static final long serialVersionUID = 1L;

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
     * @return USER_ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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