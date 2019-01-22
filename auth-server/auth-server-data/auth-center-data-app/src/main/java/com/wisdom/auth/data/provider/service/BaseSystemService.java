package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.BaseSystem;
import com.wisdom.auth.data.api.pojo.response.ModuleAndSystemResponse;
import com.wisdom.auth.data.provider.mapper.mapper.BaseSystemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseSystemService extends BaseService<BaseSystem> {
    public List<ModuleAndSystemResponse> selectModuleAndSystem() {
        return ((BaseSystemMapper)mapper).selectModuleAndSystem();
    }
}
