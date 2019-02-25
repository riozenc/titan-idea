package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "MENU_INFO")
public class MenuInfo implements Serializable,Cloneable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MENU_CODE")
    private String menuCode;

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "MENU_URL")
    private String menuUrl;

    @Column(name = "PARENT_ID")
    private Integer parentId;

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
     * 子菜单
     */
    @Transient
    private List<MenuInfo> subModules;
    /**
     * 菜单里按钮
     */
    @Transient
    private List<MenuRightInfo> operatings;

    /**
     * 资源所属系统
     */
    @Transient
    private String projectName;
    /**
     * 角色对应的操作按钮
     */
    @Transient
    private Integer button;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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

    public List<MenuInfo> getSubModules() {
        return subModules;
    }

    public void setSubModules(List<MenuInfo> subModules) {
        this.subModules = subModules;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<MenuRightInfo> getOperatings() {
        return operatings;
    }

    public void setOperatings(List<MenuRightInfo> operatings) {
        this.operatings = operatings;
    }

    public Integer getButton() {
        return button;
    }

    public void setButton(Integer button) {
        this.button = button;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public Object clone(){
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e){
            return null;
        }
    }
}