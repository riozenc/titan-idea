package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.BaseUser;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.BaseUserRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by fp295 on 2018/4/9.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseUserService.HystrixClientFallback.class)
public interface BaseUserService extends BaseUserRemoteService {

    class HystrixClientFallback implements BaseUserService{

        @Override
        public ResponseData<BaseUser> getUserByUserName(@PathVariable("userName") String userName) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<BaseUser> getUserByPhone(@PathVariable("phone") String phone) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
