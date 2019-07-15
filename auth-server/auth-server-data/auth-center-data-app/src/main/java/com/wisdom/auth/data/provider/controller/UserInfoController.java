package com.wisdom.auth.data.provider.controller;

import com.github.pagehelper.PageInfo;
import com.wisdom.auth.autoconfigure.controller.CrudController;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.common.pojo.TableData;
import com.wisdom.auth.data.api.mapper.model.RoleInfo;
import com.wisdom.auth.data.api.mapper.model.UserInfo;
import com.wisdom.auth.data.api.mapper.model.UserRoleRel;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.api.pojo.request.UserInfoRequest;
import com.wisdom.auth.data.api.service.UserInfoRemoteService;
import com.wisdom.auth.data.provider.service.UserInfoService;
import com.wisdom.auth.data.provider.service.UserRoleRelService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by yxs on 2019/1/9.
 */
@RestController
public class UserInfoController extends CrudController<UserInfo, UserInfoRequest> implements UserInfoRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRoleRelService userRoleRelService;

    /**
     * 根据用户名获取用户信息
     * @param userId
     * @return
     */
    @Override
    public ResponseData<UserInfo> getUserByUserName(@PathVariable("userId") String userId) {
        logger.debug("根据用户名查询用户");
        if(StringUtils.isEmpty(userId)){
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        UserInfo userInfo = new UserInfo();
//        userInfo.setUserName(userName);
        userInfo.setUserId(userId);
        userInfo = userInfoService.selectOne(userInfo);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage(), userInfo);
    }

    /**
     * 根据电话号码查询用户信息
     * @param phone
     * @return
     */
    @Override
    public ResponseData<UserInfo> getUserByPhone(@PathVariable("phone") String phone) {
        logger.debug("根据电话号码查询用户");
        if(StringUtils.isEmpty(phone)){
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo = userInfoService.selectOne(userInfo);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage(), userInfo);
    }

    @PostMapping("/user/table")
    @Override
    protected ResponseData<TableData<UserInfo>> queryRecord(@RequestBody UserInfoRequest query) {
        logger.debug("查询用户");
        Example example = new Example(UserInfo.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getUserId())) {
            criteria.andEqualTo("userId",  query.getUserId());
        }
        if(!StringUtils.isEmpty(query.getUserName())) {
            criteria.andLike("userName", "%" + query.getUserName() + "%");
        }
        if(!StringUtils.isEmpty(query.getPhone())) {
            criteria.andLike("phone", "%" + query.getPhone() + "%");
        }
        if(!StringUtils.isEmpty(query.getPhone())) {
            criteria.andEqualTo("status",  query.getStatus());
        }
        PageInfo<UserInfo> pageInfo = userInfoService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/user/add")
    @Override
    protected ResponseData<UserInfo> addRecord(@RequestBody UserInfo record) {
        logger.debug("添加用户");
        try {
            logger.debug("用户密码加密");
//            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            record.setPassword(new BCryptPasswordEncoder(6).encode(record.getPassword()));
            userInfoService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }

    @PostMapping("/user/delete")
    @Override
        protected ResponseData<UserInfo> deleteRecord(@RequestBody List<UserInfo> record) {
        logger.debug("删除用户");
        try {
            userInfoService.deleteBatch(record);
        } catch (Exception e) {
            logger.error("删除用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }

    @PostMapping("/user/update")
    @Override
    protected ResponseData<UserInfo> updateRecord(@RequestBody UserInfo record) {
        logger.debug("更新用户");
        try {
            record.setPassword(new BCryptPasswordEncoder(6).encode(record.getPassword()));
//            record.setPassword(null);
            record.setUpdateDate(new Date());
            userInfoService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 重置密码
     * @param record
     * @return
     */
    @PostMapping("/user/password/{newPassword}")
    public ResponseData<UserInfo> resetPassword(@RequestBody List<UserInfo> record, @PathVariable("newPassword") String newPassword) {
        logger.debug("重置密码");
        try {
            userInfoService.resetPassword(record, newPassword);
        } catch (Exception e) {
            logger.error("重置密码用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/user/role/{userId}")
    public ResponseData<List<UserRoleRel>> saveUserRole(@PathVariable("userId") Integer userId) {
        logger.debug("获取用户授权角色");
        List<UserRoleRel> list;
        try {
            Example example = new Example(UserRoleRel.class);
            example.createCriteria().andEqualTo("userId", userId);
            list = userRoleRelService.selectByExample(example);
        } catch (Exception e) {
            logger.error("获取用户授权角色失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/user/role/save")
    public ResponseData<UserInfo> saveUserRole(@RequestBody List<UserRoleRel> userRoleRelList) {
        System.out.println("------------------------provider1-------------------------------"+ userRoleRelList);
        logger.debug("保存用户授权角色");
        try {
            userRoleRelService.saveUserRole(userRoleRelList);
        } catch (Exception e) {
            logger.error("保存用户授权角色失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }

    @PostMapping("/user/role/deleteUserRoleRelList")
    public ResponseData deleteUserRoleRelList(@RequestBody List<UserRoleRel> UserRoleRelList) {
        logger.debug("批量删除用户角色关联");
        try {
            userRoleRelService.deleteUserRoleRel(UserRoleRelList);
        } catch (RuntimeException e) {
            logger.error("批量删除用户角色关联失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }

        return new ResponseData(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/user/validate/{userId}")
    public ResponseData<UserInfo> validateUserName(@PathVariable("userId") String userId) {
        logger.debug("校验用户名是否存在");
        UserInfo userInfo = new UserInfo();
//        userInfo.setUserName(userName);
        userInfo.setUserId(userId);
        userInfo = userInfoService.selectOne(userInfo);
        if(userInfo == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/user/validate/phone/{phone}")
    public ResponseData<UserInfo> validatePhone(@PathVariable("phone") String phone) {
        logger.debug("校验手机号码是否存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo = userInfoService.selectOne(userInfo);
        if(userInfo == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
    }

    @PostMapping(value = "/user/getUserTableByRole")
    private ResponseData<List<UserInfo>> getUserTableByRole(@RequestBody UserInfo moduleResources) {
        logger.debug("查询该角色下的用户");
        List<UserInfo> list;
        try {
            list = userInfoService.getUserTableByRoleId(moduleResources);
        } catch (Exception e) {
            logger.error("查询模块树异常" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping(value = "/user/getUnUserTableByRole")
    private ResponseData<List<UserInfo>> getUnUserTableByRole(@RequestBody UserInfo moduleResources) {
        logger.debug("查询未关联本角色的用户");
        List<UserInfo> list;
        try {
            list = userInfoService.getUnUserTableByRoleId(moduleResources);
        } catch (Exception e) {
            logger.error("查询模块树异常" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/user/role/addUserRoleRel")
    public ResponseData<UserInfo> addUserRoleRel(@RequestBody List<UserRoleRel> userRoleRelList) {
        System.out.println("------------------------provider1-------------------------------"+ userRoleRelList);
        logger.debug("保存角色授权用户");
        try {
            userRoleRelList.forEach(it ->
                    userRoleRelService.insertSelective(it));
        } catch (Exception e) {
            logger.error("保存角色授权用户失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(),"", ResponseCode.SUCCESS.getMessage());
    }
}
