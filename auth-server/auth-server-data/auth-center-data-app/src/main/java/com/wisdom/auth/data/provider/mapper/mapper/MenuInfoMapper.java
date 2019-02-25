package com.wisdom.auth.data.provider.mapper.mapper;


import com.wisdom.auth.data.api.mapper.model.MenuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface MenuInfoMapper extends Mapper<MenuInfo> {
    List<MenuInfo> getMenusByUserId(@Param("userId") Integer userId);

    List<MenuInfo> selectModuleTree(@Param("id") Integer id, @Param("systemId") Integer systemId);
}