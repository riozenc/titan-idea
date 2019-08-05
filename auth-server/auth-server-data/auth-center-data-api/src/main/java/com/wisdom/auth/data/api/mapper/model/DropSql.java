package com.wisdom.auth.data.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "DROP_SQL")
public class DropSql implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DROPSQL")
    private String dropsql;

    @Column(name = "DROP_NAME")
    private String dropName;


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

    public String getDropsql() {
        return dropsql;
    }

    public void setDropsql(String dropsql) {
        this.dropsql = dropsql;
    }

    public String getDropName() {
        return dropName;
    }

    public void setDropName(String dropName) {
        this.dropName = dropName;
    }
}