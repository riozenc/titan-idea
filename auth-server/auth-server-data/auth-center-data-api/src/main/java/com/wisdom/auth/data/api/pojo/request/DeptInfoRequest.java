package com.wisdom.auth.data.api.pojo.request;

import com.wisdom.auth.common.pojo.BaseRequestPojo;
import com.wisdom.auth.data.api.mapper.model.DeptInfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "DEPT_INFO")
public class DeptInfoRequest extends BaseRequestPojo implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "DEPT_ID")
    private String deptId;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "DEPT_TYPE")
    private Integer deptType;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "SORT_NO")
    private Integer sortNo;
    /**
     * 下级部门
     */
    @Transient
    private List<DeptInfo> subDepts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptType() {
        return deptType;
    }

    public void setDeptType(Integer deptType) {
        this.deptType = deptType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public List<DeptInfo> getSubDepts() {
        return subDepts;
    }

    public void setSubDepts(List<DeptInfo> subDepts) {
        this.subDepts = subDepts;
    }
}