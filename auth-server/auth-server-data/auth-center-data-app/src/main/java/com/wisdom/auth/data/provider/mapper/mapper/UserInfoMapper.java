package com.wisdom.auth.data.provider.mapper.mapper;


import com.wisdom.auth.data.api.mapper.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserInfoMapper extends Mapper<UserInfo> {
    List<UserInfo> getUserTableByRoleId(UserInfo info);
    List<UserInfo> getUnUserTableByRoleId(UserInfo info);
    UserInfo selectHegangUser(UserInfo info);
}