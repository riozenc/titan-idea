package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.RoleInfo;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.RoleInfoRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by yxs on 2019/1/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = RoleInfoService.HystrixClientFallback.class,path = "auth-data")
public interface RoleInfoService extends RoleInfoRemoteService {

    class HystrixClientFallback implements RoleInfoService {

        @Override
        public ResponseData<List<RoleInfo>> getRoleByUserId(@PathVariable("userId") Integer userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage(),ResponseCode.ERROR.getMessage());
        }
    }
}
