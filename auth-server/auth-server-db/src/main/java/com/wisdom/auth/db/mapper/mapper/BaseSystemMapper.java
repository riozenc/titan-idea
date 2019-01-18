package com.wisdom.auth.db.mapper.mapper;


import com.wisdom.auth.db.mapper.model.BaseSystem;
import com.wisdom.auth.db.pojo.response.ModuleAndSystemResponse;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseSystemMapper extends Mapper<BaseSystem> {
    List<ModuleAndSystemResponse> selectModuleAndSystem();
}