package com.wisdom.auth.db.feignclient;

import com.wisdom.auth.db.InterfaceService;
import com.wisdom.auth.db.mapper.model.BaseRole;
import com.wisdom.auth.common.pojo.ResponseCode;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.db.controller.service.BaseRoleRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by yxs on 2019/1/16.
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
