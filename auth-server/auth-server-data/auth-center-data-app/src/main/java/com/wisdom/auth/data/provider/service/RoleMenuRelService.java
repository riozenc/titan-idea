package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.RoleMenuRel;
import com.wisdom.auth.data.api.mapper.model.UserRoleRel;
import com.wisdom.auth.data.provider.mapper.mapper.RoleMenuRelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

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

    // 查询该角色拥有的按钮
    public List<Integer> roleMenuButton(RoleMenuRel roleMenuRel) {
        return ((RoleMenuRelMapper)mapper).roleMenuButton(roleMenuRel);
    }

    // 保存按钮权限
    public int  saveButtonAccess(RoleMenuRel roleMenuRel) {
        return ((RoleMenuRelMapper)mapper).saveButtonAccess(roleMenuRel);
    }

    // 保存角色关联的菜单
    public void  saveRoleMenuRels(List<RoleMenuRel> roleMenuRel) {
        roleMenuRel.forEach(it -> {
//                it.setId(UUID.uuid32());
                mapper.insertSelective(it);
            });
    }



    // 查询关联角色的叶子模块
    public void  deleteRoleMenuRel(RoleMenuRel roleMenuRel) {
        String menuIds="";
        for (int i = 0; i <roleMenuRel.getNeedDelList().size() ; i++) {
            if(i!=0){
                menuIds+=",";
            }
            menuIds+=roleMenuRel.getNeedDelList().get(i).toString();
        }
        Condition condition=new Condition(RoleMenuRel.class);
        condition.createCriteria().andCondition("role_Id = "+roleMenuRel.getRoleId()+"")
        .andCondition("menu_Id in ("+menuIds+")");
         mapper.deleteByExample(condition);

    }



}
