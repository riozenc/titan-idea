package com.wisdom.auth.data.api.pojo.request;

import com.wisdom.auth.common.pojo.BaseRequestPojo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="DROP_SQL")
public class DropSqlRequest extends BaseRequestPojo
        implements Serializable
{

    @Id
    @Column(name="ID")
    private Integer id;

    @Column(name="DROPSQL")
    private String dropsql;

    @Column(name="DROP_NAME")
    private String dropName;
    private static final long serialVersionUID = 1L;

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDropsql() {
        return this.dropsql;
    }

    public void setDropsql(String dropsql) {
        this.dropsql = dropsql;
    }

    public String getDropName() {
        return this.dropName;
    }

    public void setDropName(String dropName) {
        this.dropName = dropName;
    }
}