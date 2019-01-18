package com.wisdom.auth.db.service;

import com.wisdom.auth.common.db.service.BaseService;
import com.wisdom.auth.db.mapper.mapper.BaseRoleMapper;
import com.wisdom.auth.db.mapper.model.BaseRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yxs on 2019/1/18.
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
