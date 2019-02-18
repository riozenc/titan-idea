package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.RoleDeptRel;
import com.wisdom.auth.data.provider.mapper.mapper.RoleDeptRelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class RoleDeptRelService extends BaseService<RoleDeptRel> {

    @Transactional
    public void saveRoleDept(List<RoleDeptRel> roleDept) {
        if (roleDept.size() > 0 && roleDept.get(0).getRoleId()!=null) {
            RoleDeptRel deptRel = new RoleDeptRel();
            deptRel.setRoleId(roleDept.get(0).getRoleId());
            mapper.delete(deptRel);
            roleDept.forEach(it -> {
//                it.setId(UUID.uuid32());
                mapper.insertSelective(it);
            });
        }
    }

    // 查询关联角色的叶子模块
    public List<RoleDeptRel> selectRoleDept(String roleId) {
        return ((RoleDeptRelMapper)mapper).selectRoleDept(roleId);
    }
}
