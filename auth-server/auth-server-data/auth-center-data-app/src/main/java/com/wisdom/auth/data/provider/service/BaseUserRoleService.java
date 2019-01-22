package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.common.utils.UUID;
import com.wisdom.auth.data.api.mapper.model.BaseUserRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseUserRoleService extends BaseService<BaseUserRole> {

    /**
     * 保存用户角色
     * @param baseUserRoleList
     */
    @Transactional
    public void saveUserRole(List<BaseUserRole> baseUserRoleList) {
        if (baseUserRoleList.size() > 0 && !StringUtils.isEmpty(baseUserRoleList.get(0).getRoleId())) {
            BaseUserRole userRole = new BaseUserRole();
            userRole.setUserId(baseUserRoleList.get(0).getUserId());
            mapper.delete(userRole);
            baseUserRoleList.forEach(it -> {
                it.setId(UUID.uuid32());
                insertSelective(it);
            });
        }
    }
}
