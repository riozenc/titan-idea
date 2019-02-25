package com.wisdom.auth.data.provider.controller;

import com.github.pagehelper.PageInfo;
import com.wisdom.auth.autoconfigure.controller.CrudController;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.common.pojo.TableData;
import com.wisdom.auth.common.utils.UUID;
import com.wisdom.auth.data.api.mapper.model.SystemInfo;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.pojo.request.SystemInfoRequest;
import com.wisdom.auth.data.api.pojo.response.ModuleAndSystemResponse;
import com.wisdom.auth.data.provider.service.SystemInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by fp295 on 2018/5/13.
 */
@RestController
public class SystemInfoController extends CrudController<SystemInfo, SystemInfoRequest> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemInfoService systemInfoService;

    @PostMapping("/system/table")
    @Override
    protected ResponseData<TableData<SystemInfo>> queryRecord(@RequestBody SystemInfoRequest query) {
        logger.debug("查询系统表格");
        Example example = new Example(SystemInfo.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getProjectName())) {
            criteria.andLike("projectName", "%" + query.getProjectName() + "%");
        }
        if(!StringUtils.isEmpty(query.getSystemName())) {
            criteria.andLike("systemName", "%" + query.getSystemName() + "%");
        }

        PageInfo<SystemInfo> pageInfo = systemInfoService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/system/add")
    @Override
    protected ResponseData<SystemInfo> addRecord(@RequestBody SystemInfo record) {
        logger.debug("添加系统");
        try {
//            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            systemInfoService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PostMapping("/system/delete")
    @Override
    protected ResponseData<SystemInfo> deleteRecord(@RequestBody List<SystemInfo> record) {
        logger.debug("删除系统");
        try {
            systemInfoService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PostMapping("/system/update")
    @Override
    protected ResponseData<SystemInfo> updateRecord(@RequestBody SystemInfo record) {
        logger.debug("更新系统");
        try {
            record.setUpdateDate(new Date());
            systemInfoService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/system/validate/{projectName}")
    public ResponseData<SystemInfo> validateRoleCode(@PathVariable("projectName") String projectName) {
        logger.debug("校验系统项目名是否存在");
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setProjectName(projectName);
        systemInfo = systemInfoService.selectOne(systemInfo);
        if(systemInfo == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/system/all")
    public ResponseData<List<SystemInfo>> getSystem() {
        logger.debug("查询所有系统");
        List<SystemInfo> list;
        try {
            list = systemInfoService.selectAll();
        } catch (Exception e) {
            logger.error("查询所有系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/system/menu")
    public ResponseData<List<ModuleAndSystemResponse>> getModuleAndSystem() {
        logger.debug("查询系统及模块树");
        List<ModuleAndSystemResponse> list;
        try {
            list = systemInfoService.selectModuleAndSystem();
        } catch (Exception e) {
            logger.error("查询系统及模块树失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

}
