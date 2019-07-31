package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.DropSql;
import com.wisdom.auth.data.api.mapper.model.SystemInfo;
import com.wisdom.auth.data.api.pojo.response.ModuleAndSystemResponse;
import com.wisdom.auth.data.provider.mapper.mapper.DropSqlMapper;
import com.wisdom.auth.data.provider.mapper.mapper.SystemInfoMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class DropSqlService extends BaseService<DropSql> {
    public List<DropSql> selectDropSql() {
        return ((DropSqlMapper)mapper).selectDropSql();
    }
    public List<LinkedHashMap<String, Object>> findBySql(String sql) {
        return ((DropSqlMapper)mapper).findBySql(sql);

    }
}
