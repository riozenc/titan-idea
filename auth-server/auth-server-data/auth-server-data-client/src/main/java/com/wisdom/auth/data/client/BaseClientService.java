package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.OauthClientDetails;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.BaseClientRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
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
