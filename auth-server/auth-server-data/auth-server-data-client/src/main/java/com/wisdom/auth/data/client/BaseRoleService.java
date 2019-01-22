package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.BaseRole;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.BaseRoleRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by fp295 on 2018/4/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseRoleService.HystrixClientFallback.class)
public interface BaseRoleService extends BaseRoleRemoteService{

    class HystrixClientFallback implements BaseRoleService{

        @Override
        public ResponseData<List<BaseRole>> getRoleByUserId(@PathVariable("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
