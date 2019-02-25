package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.MenuInfoRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by yxs on 2019/1/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = MenuInfoService.HystrixClientFallback.class,path = "/auth-data")
public interface MenuInfoService extends MenuInfoRemoteService {

    class HystrixClientFallback implements MenuInfoService {

        @Override
        public ResponseData<List<MenuInfo>> getMenusByUserId(@PathVariable("userId") Integer userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
