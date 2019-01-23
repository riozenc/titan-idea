package com.wisdom.auth.data.api.service;


import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.mapper.model.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yxs on 2019/1/9.
 */
public interface UserInfoRemoteService {

    /**
     * 根据用户名查询用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/name/{userId}", method = RequestMethod.GET)
    ResponseData<UserInfo> getUserByUserName(@PathVariable("userId") String userId);

    /**
     * 根据电话号码查询用户
     * @param phone
     * @return
     */
    @RequestMapping(value = "/user/phone/{phone}", method = RequestMethod.GET)
    ResponseData<UserInfo> getUserByPhone(@PathVariable("phone") String phone);
}
