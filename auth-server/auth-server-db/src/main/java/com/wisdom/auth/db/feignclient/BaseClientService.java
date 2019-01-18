package com.wisdom.auth.db.feignclient;

import com.wisdom.auth.common.pojo.ResponseCode;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.db.InterfaceService;
import com.wisdom.auth.db.mapper.model.OauthClientDetails;
import com.wisdom.auth.db.controller.service.BaseClientRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * Created by yxs on 2019/1/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseClientService.HystrixClientFallback.class)
public interface BaseClientService extends BaseClientRemoteService {

    class HystrixClientFallback implements BaseClientService {

        @Override
        public ResponseData<List<OauthClientDetails>> getAllClient() {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
