package com.wisdom.auth.data.api.service;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.mapper.model.OauthClientDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yxs on 2019/1/16.
 */
public interface BaseClientRemoteService {

    @RequestMapping(value = "/client/all", method = RequestMethod.GET)
    ResponseData<List<OauthClientDetails>> getAllClient();

}
