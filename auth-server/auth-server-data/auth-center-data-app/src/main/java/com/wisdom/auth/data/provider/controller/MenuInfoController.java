package com.wisdom.auth.data.provider.controller;

import com.github.pagehelper.PageInfo;
import com.wisdom.auth.autoconfigure.controller.CrudController;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.common.pojo.TableData;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;
import com.wisdom.auth.data.api.mapper.model.RoleMenuRel;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.pojo.request.MenuInfoRequest;
import com.wisdom.auth.data.api.service.MenuInfoRemoteService;
import com.wisdom.auth.data.provider.redis.AccessTokenUtils;
import com.wisdom.auth.data.provider.service.MenuInfoService;
import com.wisdom.auth.data.provider.service.RoleMenuRelService;
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
public class MenuInfoController extends CrudController<MenuInfo, MenuInfoRequest> implements MenuInfoRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Autowired
    private RoleMenuRelService roleMenuRelService;

    @Override
    public ResponseData<List<MenuInfo>> getMenusByUserId(@PathVariable("userId") Integer userId) {
        logger.debug("根据用户查询菜单");
        List<MenuInfo> list;
        try {
            list = menuInfoService.getMenusByUserId(userId);
        }catch (Exception e){
            logger.error("根据用户查询菜单错误");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/menu")
    public ResponseData<List<MenuInfo>> getCurrentMenu() {
        System.out.println("--------------/menu----------provider1-------------------------------"+accessTokenUtils.getMenuInfo());
        logger.debug("查询当前用户菜单");
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), accessTokenUtils.getMenuInfo());//
    }

    @PostMapping(value = "/module/tree")
    private ResponseData<List<MenuInfo>> getModuleTree(@RequestBody MenuInfo moduleResources) {
        logger.debug("查询模块树");
        List<MenuInfo> list;
        try {
            list = menuInfoService.getModuleTree(moduleResources.getId(), moduleResources.getSystemId());
        } catch (Exception e) {
            logger.error("查询模块树异常" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/module/table")
    @Override
    protected ResponseData<TableData<MenuInfo>> queryRecord(@RequestBody MenuInfoRequest query) {
        logger.debug("查询模块表格");
        Example example = new Example(MenuInfo.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(query.getParentId())) {
            criteria.andEqualTo("parentId", query.getParentId());
        } else if (!StringUtils.isEmpty(query.getSystemId())) {
            criteria.andEqualTo("systemId",  query.getSystemId());
            criteria.andIsNull("parentId");
        } else {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        example.orderBy("sort");
        PageInfo<MenuInfo> pageInfo = menuInfoService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/module")
    @Override
    protected ResponseData<MenuInfo> addRecord(@RequestBody MenuInfo record) {
        logger.debug("添加模块");
        try {
//            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            menuInfoService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/module")
    @Override
    protected ResponseData<MenuInfo> deleteRecord(@RequestBody List<MenuInfo> record) {
        logger.debug("删除模块");
        try {
            menuInfoService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/module")
    @Override
    protected ResponseData<MenuInfo> updateRecord(@RequestBody MenuInfo record) {
        logger.debug("更新模块");
        try {
            record.setUpdateDate(new Date());
            menuInfoService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新模块失败：" + e.getMessage());
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
//        menuInfo = menuInfoService.selectOne(menuInfo);
//        if(menuInfo == null) {
//            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
//        }
//        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
//    }

    @PostMapping("/module/role")
    public ResponseData saveRoleResourcesAuth(@RequestBody List<RoleMenuRel> roleModule) {
        logger.debug("保存角色权限");
        try {
            roleMenuRelService.saveRoleModule(roleModule);
        } catch (RuntimeException e) {
            logger.error("保存角色权限失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }

        return new ResponseData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }
}
