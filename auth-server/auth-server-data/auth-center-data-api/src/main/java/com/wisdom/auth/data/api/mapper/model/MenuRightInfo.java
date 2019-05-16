package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "MENU_RIGHT_INFO")
public class MenuRightInfo implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MENU_ID")
    private Integer menuId;

    @Column(name = "RIGHT_NAME")
    private String rightName;

    /**
     * 2的n次方
     */
    @Column(name = "RIGHT_DESCRIBE")
    private Integer rightDescribe;

    @Column(name = "SORT")
    private Integer sort;

    @Transient
    private Integer key;

    @Transient
    private String label;

    @Transient
    private Boolean disable;




    private static final long serialVersionUID = 1L;


    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public Integer getRightDescribe() {
        return rightDescribe;
    }

    public void setRightDescribe(Integer rightDescribe) {
        this.rightDescribe = rightDescribe;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}