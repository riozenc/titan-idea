package com.wisdom.auth.data.api.pojo.request;

import com.wisdom.auth.common.pojo.BaseRequestPojo;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "ROLE_INFO")
public class RoleInfoRequest extends BaseRequestPojo implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_NO")
    private String roleNo;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    /**
     * 角色资源
     */
    @Transient
    private List<MenuInfo> modules;

    private static final long serialVersionUID = 1L;


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
     * @return ROLE_CODE
     */
    public String getRoleNo() {
        return roleNo;
    }

    /**
     * @param roleNo
     */
    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    /**
     * @return ROLE_NAME
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return UPDATE_DATE
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<MenuInfo> getModules() {
        return modules;
    }

    public void setModules(List<MenuInfo> modules) {
        this.modules = modules;
    }
}