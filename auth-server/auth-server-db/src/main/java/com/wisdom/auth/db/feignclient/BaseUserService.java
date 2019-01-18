package com.wisdom.auth.db.feignclient;

import com.wisdom.auth.db.InterfaceService;
import com.wisdom.auth.db.mapper.model.BaseUser;
import com.wisdom.auth.common.pojo.ResponseCode;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.db.controller.service.BaseUserRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by yxs on 2019/1/16.
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
