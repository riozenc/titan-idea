package com.wisdom.auth.data.provider.mapper.mapper;


import com.wisdom.auth.data.api.mapper.model.RoleDeptRel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RoleDeptRelMapper extends Mapper<RoleDeptRel> {
    List<RoleDeptRel> selectRoleDept(String roleId);
}