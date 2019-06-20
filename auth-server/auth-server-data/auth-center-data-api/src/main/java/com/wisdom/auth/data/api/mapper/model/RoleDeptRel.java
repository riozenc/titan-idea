package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "ROLE_DEPT_REL")
public class RoleDeptRel implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "DEPT_ID")
    private Integer deptId;

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
     * @return DEPT_ID
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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