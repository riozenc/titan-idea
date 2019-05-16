package com.wisdom.auth.data.api.pojo.request;

import com.wisdom.auth.common.pojo.BaseRequestPojo;
import com.wisdom.auth.data.api.mapper.model.DeptInfo;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "MENU_INFO")
public class MenuInfoRequest extends BaseRequestPojo implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MENU_CODE")
    private String menuCode;

    @Column(name = "MENU_NAME")
    private String title;

    @Column(name = "MENU_URL")
    private String menuUrl;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "IMG_SRC")
    private String imgSrc;

    /**
     * 0 否，1 是
     */
    @Column(name = "IS_LEAF")
    private Integer isLeaf;

    @Column(name = "SORT_NO")
    private Integer sortNo;

    @Column(name = "SYSTEM_ID")
    private Integer systemId;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    /**
     * 资源子项
     */
    @Transient
    private List<MenuInfoRequest> children;

    @Transient
    private boolean  checked;

    @Transient
    private boolean  expand;

    /**
     * 资源所属系统
     */
    @Transient
    private String projectName;

    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public List<MenuInfoRequest> getChildren() {
        return children;
    }

    public void setChildren(List<MenuInfoRequest> children) {
        this.children = children;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}