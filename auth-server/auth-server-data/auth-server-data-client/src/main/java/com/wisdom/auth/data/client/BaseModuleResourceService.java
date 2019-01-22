package com.wisdom.auth.data.client;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.InterfaceService;
import com.wisdom.auth.data.api.mapper.model.BaseModuleResources;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.service.BaseModuleResourcesRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by fp295 on 2018/4/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseModuleResourceService.HystrixClientFallback.class)
public interface BaseModuleResourceService extends BaseModuleResourcesRemoteService {

    class HystrixClientFallback implements BaseModuleResourceService{

        @Override
        public ResponseData<List<BaseModuleResources>> getMenusByUserId(@PathVariable("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
