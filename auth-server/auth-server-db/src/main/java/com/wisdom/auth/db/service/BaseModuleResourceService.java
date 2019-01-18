package com.wisdom.auth.db.service;

import com.wisdom.auth.common.db.service.BaseService;
import com.wisdom.auth.db.mapper.mapper.BaseModuleResourcesMapper;
import com.wisdom.auth.db.mapper.model.BaseModuleResources;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yxs on 2019/1/18.
 */
@Service
public class BaseModuleResourceService extends BaseService<BaseModuleResources> {


    /**
     * 根据用户查询菜单
     * @param userId
     * @return
     */
    public List<BaseModuleResources> getMenusByUserId(String userId) {
        return ((BaseModuleResourcesMapper)mapper).getMenusByUserId(userId);
    }

    public List<BaseModuleResources> getModuleTree(String id, String systemId) {
        return ((BaseModuleResourcesMapper)mapper).selectModuleTree(id, systemId);
    }
}
