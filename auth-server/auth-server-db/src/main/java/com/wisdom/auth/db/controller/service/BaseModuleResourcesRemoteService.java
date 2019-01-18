package com.wisdom.auth.db.controller.service;

import com.wisdom.auth.db.mapper.model.BaseModuleResources;
import com.wisdom.auth.common.pojo.ResponseData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yxs on 2019/1/16.
 */
public interface BaseModuleResourcesRemoteService {

    /**
     * 根据userId查询菜单
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menu/user/{userId}", method = RequestMethod.GET)
    ResponseData<List<BaseModuleResources>> getMenusByUserId(@PathVariable("userId") String userId);
}
