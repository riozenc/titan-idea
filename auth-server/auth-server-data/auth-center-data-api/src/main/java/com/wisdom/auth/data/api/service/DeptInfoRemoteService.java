package com.wisdom.auth.data.api.service;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.mapper.model.DeptInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yxs on 2019/1/16.
 */
public interface DeptInfoRemoteService {

    /**
     * 根据userId查询组织机构
     * @param userId
     * @return
     */
    @RequestMapping(value = "/dept/user/{userId}", method = RequestMethod.GET)
    ResponseData<List<DeptInfo>> getDeptsByUserId(@PathVariable("userId") Integer userId);
}
