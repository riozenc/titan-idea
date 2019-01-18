package com.wisdom.auth.db.service;

import com.wisdom.auth.common.db.service.BaseService;
import com.wisdom.auth.db.mapper.mapper.BaseSystemMapper;
import com.wisdom.auth.db.mapper.model.BaseSystem;
import com.wisdom.auth.db.pojo.response.ModuleAndSystemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yxs on 2019/1/18.
 */
@Service
public class BaseSystemService extends BaseService<BaseSystem> {
    public List<ModuleAndSystemResponse> selectModuleAndSystem() {
        return ((BaseSystemMapper)mapper).selectModuleAndSystem();
    }
}
