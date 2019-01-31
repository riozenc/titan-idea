package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.RoleMenuRel;
import com.wisdom.auth.data.provider.mapper.mapper.RoleMenuRelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class RoleMenuRelService extends BaseService<RoleMenuRel> {

    @Transactional
    public void saveRoleModule(List<RoleMenuRel> roleModule) {
        if (roleModule.size() > 0 && roleModule.get(0).getRoleId()!=null) {
            RoleMenuRel module = new RoleMenuRel();
            module.setRoleId(roleModule.get(0).getRoleId());
            mapper.delete(module);
            roleModule.forEach(it -> {
//                it.setId(UUID.uuid32());
                mapper.insertSelective(it);
            });
        }
    }

    // 查询关联角色的叶子模块
    public List<RoleMenuRel> selectLeafRoleModule(String roleId) {
        return ((RoleMenuRelMapper)mapper).selectLeafRoleModule(roleId);
    }
}
