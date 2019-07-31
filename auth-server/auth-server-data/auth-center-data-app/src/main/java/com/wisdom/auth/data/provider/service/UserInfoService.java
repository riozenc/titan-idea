package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.UserInfo;
import com.wisdom.auth.data.api.mapper.model.UserRoleRel;
import com.wisdom.auth.data.provider.annotation.DataSource;
import com.wisdom.auth.data.provider.enums.DataSourceEnum;
import com.wisdom.auth.data.provider.mapper.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class UserInfoService extends BaseService<UserInfo> {

    @Autowired
    private UserRoleRelService userRoleRelService;


    /**
     * 批量重置密码
     * @param record
     * @param newPassword
     */
    @Transactional
    public void resetPassword(List<UserInfo> record, String newPassword) {
        record.forEach(e -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(e.getId());
            userInfo.setPassword(new BCryptPasswordEncoder(6).encode(newPassword));
            userInfo.setUpdateDate(new Date());
            updateByPrimaryKeySelective(userInfo);
        });
    }

    /**
     * 删除用户
     * @param record
     */
    @Transactional
    public void deleteBatch(List<UserInfo> record) {
        record.forEach(e -> {
            Example example = new Example(UserRoleRel.class);
            example.createCriteria().andEqualTo("userId", e.getId());
            userRoleRelService.deleteByExample(example);
            deleteByPrimaryKey(e);
        });
    }

    public List<UserInfo> getUserTableByRoleId(UserInfo info) {
        return ((UserInfoMapper)mapper).getUserTableByRoleId(info);
    }
    public List<UserInfo> getUnUserTableByRoleId(UserInfo info) {
        return ((UserInfoMapper)mapper).getUnUserTableByRoleId(info);
    }

    @DataSource(DataSourceEnum.DB2)
    public UserInfo selectHegangUser(UserInfo info) {
        return ((UserInfoMapper)mapper).selectHegangUser(info);
    }

}
