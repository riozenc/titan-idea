package com.wisdom.auth.data.provider.controller;

import com.github.pagehelper.PageInfo;
import com.wisdom.auth.autoconfigure.controller.CrudController;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.common.pojo.TableData;
import com.wisdom.auth.data.api.mapper.model.RoleInfo;
import com.wisdom.auth.data.api.mapper.model.RoleMenuRel;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.pojo.request.RoleInfoRequest;
import com.wisdom.auth.data.api.service.RoleInfoRemoteService;
import com.wisdom.auth.data.provider.service.RoleMenuRelService;
import com.wisdom.auth.data.provider.service.RoleInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by yxs on 2019/1/17.
 */
@RestController
public class RoleInfoController extends CrudController<RoleInfo, RoleInfoRequest> implements RoleInfoRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private RoleMenuRelService roleMenuRelService;

    @Override
    public ResponseData<List<RoleInfo>> getRoleByUserId(@PathVariable("userId") Integer userId) {
        logger.debug("根据用户查询角色");
        List<RoleInfo> list;
        try {
            list = roleInfoService.getRoleByUserId(userId);
        }catch (Exception e){
            logger.error("根据用户查询角色失败");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/role")
    public ResponseData<List<RoleInfo>> getAllRole() {
        logger.debug("获取所有角色");
        List<RoleInfo> list;
        try {
            list = roleInfoService.selectAll();
        }catch (Exception e){
            logger.error("获取所有角色失败");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/role/table")
    @Override
    protected ResponseData<TableData<RoleInfo>> queryRecord(@RequestBody RoleInfoRequest query) {
        System.out.println("------------------------provider1-------------------------------"+query);
        logger.debug("查询角色表格");
        Example example = new Example(RoleInfo.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getRoleNo())) {
            criteria.andLike("roleNo", "%" + query.getRoleNo() + "%");
        }
        if(!StringUtils.isEmpty(query.getRoleName())) {
            criteria.andLike("roleName", "%" + query.getRoleName() + "%");
        }

        PageInfo<RoleInfo> pageInfo = roleInfoService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/role")
    @Override
    protected ResponseData<RoleInfo> addRecord(@RequestBody RoleInfo record) {
        logger.debug("添加角色");
        try {
//            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            roleInfoService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加角色失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/role")
    @Override
    protected ResponseData<RoleInfo> deleteRecord(@RequestBody List<RoleInfo> record) {
        logger.debug("删除角色");
        try {
            roleInfoService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除角色失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/role")
    @Override
    protected ResponseData<RoleInfo> updateRecord(@RequestBody RoleInfo record) {
        logger.debug("更新角色");
        try {
            record.setUpdateDate(new Date());
            roleInfoService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新角色失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/role/validate/{roleNo}")
    public ResponseData<RoleInfo> validateRoleCode(@PathVariable("roleNo") String roleNo) {
        logger.debug("校验角色编码是否存在");
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleNo(roleNo);
        roleInfo = roleInfoService.selectOne(roleInfo);
        if(roleInfo == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/role/auth/{roleId}")
    public ResponseData<List<RoleMenuRel>> getAuthModule(@PathVariable("roleId") String roleId) {
        logger.debug("查询角色已授权模块");
        List<RoleMenuRel> list;
        try {
            list = roleMenuRelService.selectLeafRoleModule(roleId);
        } catch (Exception e) {
            logger.error("查询角色已授权模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }
}
