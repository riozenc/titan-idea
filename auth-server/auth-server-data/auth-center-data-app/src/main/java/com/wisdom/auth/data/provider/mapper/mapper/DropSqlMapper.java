package com.wisdom.auth.data.provider.mapper.mapper;


import com.wisdom.auth.data.api.mapper.model.DropSql;
import com.wisdom.auth.data.api.mapper.model.SystemInfo;
import com.wisdom.auth.data.api.pojo.response.ModuleAndSystemResponse;
import tk.mybatis.mapper.common.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface DropSqlMapper extends Mapper<DropSql> {
    List<DropSql> selectDropSql();
    List<LinkedHashMap<String, Object>> findBySql(String sql);
}