package com.wisdom.auth.db.service;

import com.wisdom.auth.common.db.service.BaseService;
import com.wisdom.auth.db.mapper.model.BaseUser;
import com.wisdom.auth.db.mapper.model.BaseUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by yxs on 2019/1/18.
 */
@Service
public class BaseUserService extends BaseService<BaseUser> {

    @Autowired
    private BaseUserRoleService baseUserRoleService;


    /**
     * 批量重置密码
     * @param record
     * @param newPassword
     */
    @Transactional
    public void resetPassword(List<BaseUser> record, String newPassword) {
        record.forEach(e -> {
            BaseUser baseUser = new BaseUser();
            baseUser.setId(e.getId());
            baseUser.setPassword(new BCryptPasswordEncoder(6).encode(newPassword));
            baseUser.setUpdateDate(new Date());
            updateByPrimaryKeySelective(baseUser);
        });
    }

    /**
     * 删除用户
     * @param record
     */
    @Transactional
    public void deleteBatch(List<BaseUser> record) {
        record.forEach(e -> {
            Example example = new Example(BaseUserRole.class);
            example.createCriteria().andEqualTo("userId", e.getId());
            baseUserRoleService.deleteByExample(example);
            deleteByPrimaryKey(e);
        });
    }
}
