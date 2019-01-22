package com.wisdom.auth.data.provider.mapper.mapper;


import com.wisdom.auth.data.api.mapper.model.BaseRoleModule;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseRoleModuleMapper extends Mapper<BaseRoleModule> {
    List<BaseRoleModule> selectLeafRoleModule(String roleId);
}