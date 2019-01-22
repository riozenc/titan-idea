package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.BaseRole;
import com.wisdom.auth.data.provider.mapper.mapper.BaseRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseRoleService extends BaseService<BaseRole> {

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    public List<BaseRole> getRoleByUserId(String userId) {
        return ((BaseRoleMapper)mapper).getRoleByUserId(userId);
    }
}
