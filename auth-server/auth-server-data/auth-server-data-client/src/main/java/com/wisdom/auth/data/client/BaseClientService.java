package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.OauthClientDetails;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.BaseClientRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseClientService.HystrixClientFallback.class,path = "sys-data")
public interface BaseClientService extends BaseClientRemoteService {

    class HystrixClientFallback implements BaseClientService {

        @Override
        public ResponseData<List<OauthClientDetails>> getAllClient() {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
    }
}
