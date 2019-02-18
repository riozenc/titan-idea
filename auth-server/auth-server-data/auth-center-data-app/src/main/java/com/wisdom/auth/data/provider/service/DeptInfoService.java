package com.wisdom.auth.data.provider.service;

import com.wisdom.auth.autoconfigure.service.BaseService;
import com.wisdom.auth.data.api.mapper.model.DeptInfo;
import com.wisdom.auth.data.provider.mapper.mapper.DeptInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@Service
public class DeptInfoService extends BaseService<DeptInfo> {


    /**
     * 根据用户查询组织机构
     * @param userId
     * @return
     */
    public List<DeptInfo> getDeptsByUserId(Integer userId) {
        return ((DeptInfoMapper)mapper).getDeptsByUserId(userId);
    }

}
