package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
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
}