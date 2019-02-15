package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.UserInfo;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.UserInfoRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by yxs on 2019/1/9.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = UserInfoService.HystrixClientFallback.class)
public interface UserInfoService extends UserInfoRemoteService {
    @PostMapping(value = "/user")
    ResponseData<UserInfo> addRecord(@RequestBody UserInfo record);

    class HystrixClientFallback implements UserInfoService {

        @Override
        public ResponseData<UserInfo> getUserByUserName(@PathVariable("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<UserInfo> getUserByPhone(@PathVariable("phone") String phone) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<UserInfo> addRecord(@RequestBody UserInfo record) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
