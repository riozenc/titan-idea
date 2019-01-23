package com.wisdom.auth.data.api.service;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yxs on 2019/1/16.
 */
public interface MenuInfoRemoteService {

    /**
     * 根据userId查询菜单
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menu/user/{userId}", method = RequestMethod.GET)
    ResponseData<List<MenuInfo>> getMenusByUserId(@PathVariable("userId") Integer userId);
}
