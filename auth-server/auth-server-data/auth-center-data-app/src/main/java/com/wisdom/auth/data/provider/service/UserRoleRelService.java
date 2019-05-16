package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.UserRoleRel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class UserRoleRelService extends BaseService<UserRoleRel> {

    /**
     * 保存用户角色
     * @param userRoleRelList
     */
    @Transactional
    public void saveUserRole(List<UserRoleRel> userRoleRelList) {
        if (userRoleRelList.size() > 0 && userRoleRelList.get(0).getRoleId()!=null) {
            UserRoleRel userRole = new UserRoleRel();
            userRole.setUserId(userRoleRelList.get(0).getUserId());
            mapper.delete(userRole);
            userRoleRelList.forEach(it -> {
//                it.setId(UUID.uuid32());
                insertSelective(it);
            });
        }
    }


}
