package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.MenuRightInfo;
import com.wisdom.auth.data.provider.mapper.mapper.MenuRightInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class MenuRightInfoService extends BaseService<MenuRightInfo> {


    /**
     * 根据用户查询菜单
     * @param menuId
     * @return
     */
    public List<MenuRightInfo> getRightsByMenuId(Integer menuId) {
        return ((MenuRightInfoMapper)mapper).getRightsByMenuId(menuId);
    }
//    检查是否有重复序号
    public List<MenuRightInfo> checkRight(MenuRightInfo menuRightInfo) {
        return mapper.select(menuRightInfo);
    }

}
