package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.RoleInfo;
import com.wisdom.auth.data.provider.mapper.mapper.RoleInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class RoleInfoService extends BaseService<RoleInfo> {

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    public List<RoleInfo> getRoleByUserId(Integer userId) {
        return ((RoleInfoMapper)mapper).getRoleByUserId(userId);
    }
}
