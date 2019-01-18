package com.wisdom.auth.db.service;

import com.wisdom.auth.common.db.service.BaseService;
import com.wisdom.auth.common.utils.UUID;
import com.wisdom.auth.db.mapper.mapper.BaseRoleModuleMapper;
import com.wisdom.auth.db.mapper.model.BaseRoleModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yxs on 2019/1/18.
 */
@Service
public class BaseRoleModuleService extends BaseService<BaseRoleModule> {

    @Transactional
    public void saveRoleModule(List<BaseRoleModule> roleModule) {
        if (roleModule.size() > 0 && !StringUtils.isEmpty(roleModule.get(0).getRoleId())) {
            BaseRoleModule module = new BaseRoleModule();
            module.setRoleId(roleModule.get(0).getRoleId());
            mapper.delete(module);
            roleModule.forEach(it -> {
                it.setId(UUID.uuid32());
                mapper.insertSelective(it);
            });
        }
    }

    // 查询关联角色的叶子模块
    public List<BaseRoleModule> selectLeafRoleModule(String roleId) {
        return ((BaseRoleModuleMapper)mapper).selectLeafRoleModule(roleId);
    }
}
