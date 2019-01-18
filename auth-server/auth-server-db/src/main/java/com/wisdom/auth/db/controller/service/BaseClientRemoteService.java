package com.wisdom.auth.db.controller.service;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.db.mapper.model.OauthClientDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yxs on 2019/1/17.
 */
public interface BaseClientRemoteService {

    @RequestMapping(value = "/client/all", method = RequestMethod.GET)
    ResponseData<List<OauthClientDetails>> getAllClient();

}
