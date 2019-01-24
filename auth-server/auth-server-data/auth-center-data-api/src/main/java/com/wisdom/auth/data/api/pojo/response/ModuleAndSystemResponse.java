package com.wisdom.auth.data.api.pojo.response;


import java.util.List;

/**
 * Created by yxs on 2019/1/22.
 */
public class ModuleAndSystemResponse {

    private Integer id;

    private String moduleName;

    private List<ModuleAndSystemResponse> subModules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<ModuleAndSystemResponse> getSubModules() {
        return subModules;
    }

    public void setSubModules(List<ModuleAndSystemResponse> subModules) {
        this.subModules = subModules;
    }

}
