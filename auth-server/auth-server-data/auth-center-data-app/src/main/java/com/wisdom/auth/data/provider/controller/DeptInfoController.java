package com.wisdom.auth.data.provider.controller;

import com.github.pagehelper.PageInfo;
import com.wisdom.auth.autoconfigure.controller.CrudController;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.common.pojo.TableData;
import com.wisdom.auth.data.api.mapper.model.DeptInfo;
import com.wisdom.auth.data.api.mapper.model.RoleDeptRel;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.pojo.request.DeptInfoRequest;
import com.wisdom.auth.data.api.service.DeptInfoRemoteService;
import com.wisdom.auth.data.provider.redis.AccessTokenUtils;
import com.wisdom.auth.data.provider.service.DeptInfoService;
import com.wisdom.auth.data.provider.service.RoleDeptRelService;
import com.wisdom.auth.data.provider.service.RoleMenuRelService;
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
public class DeptInfoController extends CrudController<DeptInfo, DeptInfoRequest> implements DeptInfoRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeptInfoService deptInfoService;

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Autowired
    private RoleDeptRelService roleDeptRelService;

    @Override
    public ResponseData<List<DeptInfo>> getDeptsByUserId(@PathVariable("userId") Integer userId) {
        logger.debug("根据用户查询组织机构");
        List<DeptInfo> list;
        try {
            list = deptInfoService.getDeptsByUserId(userId);
        }catch (Exception e){
            logger.error("根据用户查询组织机构错误");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/dept")
    public ResponseData<List<DeptInfo>> getCurrentDept() {
        System.out.println("--------------/menu----------provider1-------------------------------"+accessTokenUtils.getDeptInfo());
        logger.debug("查询当前用户组织机构");
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), accessTokenUtils.getDeptInfo());//
    }

//    @PostMapping(value = "/menu/tree")
//    private ResponseData<List<DeptInfo>> getModuleTree(@RequestBody DeptInfo moduleResources) {
//        logger.debug("查询模块树");
//        List<DeptInfo> list;
//        try {
//            list = deptInfoService.getModuleTree(moduleResources.getId(), moduleResources.getSystemId());
//        } catch (Exception e) {
//            logger.error("查询模块树异常" + e.getMessage());
//            e.printStackTrace();
//            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
//        }
//        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
//    }

    @PostMapping("/dept/table")
    @Override
    protected ResponseData<TableData<DeptInfo>> queryRecord(@RequestBody DeptInfoRequest query) {
        logger.debug("查询组织机构表格");
        Example example = new Example(DeptInfo.class);
        Example.Criteria criteria = example.createCriteria();

        if (query.getParentId()!=null&&query.getParentId()>0) {
            criteria.andEqualTo("parentId", query.getParentId());
        } else {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        example.orderBy("sort");
        PageInfo<DeptInfo> pageInfo = deptInfoService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/dept")
    @Override
    protected ResponseData<DeptInfo> addRecord(@RequestBody DeptInfo record) {
        logger.debug("添加组织机构");
        try {
//            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            deptInfoService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加组织机构失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/dept")
    @Override
    protected ResponseData<DeptInfo> deleteRecord(@RequestBody List<DeptInfo> record) {
        logger.debug("删除组织机构");
        try {
            deptInfoService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除组织机构失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/dept")
    @Override
    protected ResponseData<DeptInfo> updateRecord(@RequestBody DeptInfo record) {
        logger.debug("更新组织机构");
        try {
//            record.setUpdateDate(new Date());
            deptInfoService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新组织机构失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

//    @GetMapping("/module/validate/{moduleCode}")
//    public ResponseData<MenuInfo> validateModuleCode(@PathVariable("moduleCode") String moduleCode) {
//        logger.debug("校验模块编码是否存在");
//        MenuInfo menuInfo = new MenuInfo();
//        menuInfo.setModuleCode(moduleCode);
//        menuInfo = deptInfoService.selectOne(menuInfo);
//        if(menuInfo == null) {
//            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
//        }
//        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
//    }

    @PostMapping("/dept/role")
    public ResponseData saveRoleDeptAuth(@RequestBody List<RoleDeptRel> roleDept) {
        logger.debug("保存角色权限");
        try {
            roleDeptRelService.saveRoleDept(roleDept);
        } catch (RuntimeException e) {
            logger.error("保存角色权限失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }

        return new ResponseData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }
}
