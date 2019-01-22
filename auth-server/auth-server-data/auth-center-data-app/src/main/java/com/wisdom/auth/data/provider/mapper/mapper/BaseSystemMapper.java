package com.wisdom.auth.data.provider.mapper.mapper;


import com.wisdom.auth.data.api.mapper.model.BaseSystem;
import com.wisdom.auth.data.api.pojo.response.ModuleAndSystemResponse;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseSystemMapper extends Mapper<BaseSystem> {
    List<ModuleAndSystemResponse> selectModuleAndSystem();
}