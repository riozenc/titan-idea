package com.wisdom.auth.provider.service;

import com.wisdom.auth.common.utils.JsonUtils;
import com.wisdom.auth.db.feignclient.BaseModuleResourceService;
import com.wisdom.auth.db.feignclient.BaseRoleService;
import com.wisdom.auth.db.feignclient.BaseUserService;
import com.wisdom.auth.db.mapper.model.BaseModuleResources;
import com.wisdom.auth.db.mapper.model.BaseRole;
import com.wisdom.auth.db.mapper.model.BaseUser;
import com.wisdom.auth.common.pojo.ResponseCode;
import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.provider.config.auth.filter.MyLoginAuthenticationFilter;
import com.wisdom.auth.provider.pojo.BaseUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 基于资源认证
 * TODO 异构系统不同的资源(用户和角色)如何认证？
 * TODO 用户，角色，权限，是不是应该通用？ 平台内应做成通用公共服务
 * TODO 第三方  要按公共通用平台的接入服务(用户,token,默认角色)进行认证和授权
 *
 *
 *  授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
 */
@Service
public class BaseUserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private BaseRoleService baseRoleService;
    @Autowired
    private BaseModuleResourceService baseModuleResourceService;
    @Autowired
    private RedisTemplate<String, BaseRole> redisTemplate;
    @Autowired
    private RedisTemplate<String, BaseModuleResources> resourcesTemplate;

    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException {
        //user&:@test
        BaseUser baseUser;
        String[] parameter;
        int index = var1.indexOf("&:@");
        System.out.println("-------------"+var1);
        if (index != -1) {
            parameter = var1.split("&:@");
        }else {
            // 如果是 refresh_token 不分割
            parameter = new String[]{MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_DEFAULT, var1};
        }

        // 手机验证码调用FeignClient根据电话号码查询用户 TODO 多个clinet?
        if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(parameter[0])){
            ResponseData<BaseUser> baseUserResponseData =null;
            //if(null!=baseUserService.getUserByPhone(parameter[1])){
                baseUserResponseData=baseUserService.getUserByPhone(parameter[1]);
             //   System.out.println("====baseUserService.getUserByPhone=====");
//            }else{
//                baseUserResponseData=baseUserService2.getUserByPhone(parameter[1]);
//                System.out.println("====baseUserService2.getUserByPhone=====");
//            }

            if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
                logger.error("找不到该用户，手机号码：" + parameter[1]);
                throw new UsernameNotFoundException("找不到该用户，手机号码：" + parameter[1]);
            }
            baseUser = baseUserResponseData.getData();
        } else if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(parameter[0])){
            // 扫码登陆根据token从redis查询用户
            baseUser = null;
        } else {
            // 账号密码登陆调用FeignClient根据用户名查询用户 TODO 多个clinet?
           ResponseData<BaseUser> baseUserResponseData = null;
            //if(null!=baseUserService.getUserByUserName(parameter[1])) {
                baseUserResponseData= baseUserService.getUserByUserName(parameter[1]);
             //   System.out.println("====baseUserService.getUserByUserName=====");
           // }else{
              //  baseUserResponseData=baseUserService2.getUserByUserName(parameter[1]);
              //  System.out.println("====baseUserService2.getUserByUserName=====");
           // }
            System.out.println("====baseUserResponseData:"+ JsonUtils.deserializer(baseUserResponseData));
            if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
                logger.error("找不到该用户，用户名：" + parameter[1]);
                throw new UsernameNotFoundException("找不到该用户，用户名：" + parameter[1]);
            }
            baseUser = baseUserResponseData.getData();
        }


        // 调用FeignClient查询角色
        ResponseData<List<BaseRole>> baseRoleListResponseData = null;
        //if(null!=baseRoleService.getRoleByUserId(baseUser.getId())){
            baseRoleListResponseData=baseRoleService.getRoleByUserId(baseUser.getId());
         //   System.out.println("====baseRoleService.getMenusByUserId(baseUser.getId()=====");
//        }else{
//            baseRoleListResponseData=baseRoleService2.getRoleByUserId(baseUser.getId());
//            System.out.println("====baseRoleService2.getMenusByUserId(baseUser.getId()=====");
//        }
        System.out.println("====baseRoleListResponseData:"+JsonUtils.deserializer(baseRoleListResponseData));
        List<BaseRole> roles;
        if(baseRoleListResponseData.getData() == null ||  !ResponseCode.SUCCESS.getCode().equals(baseRoleListResponseData.getCode())){
            logger.error("查询角色失败！");
            roles = new ArrayList<>();
        }else {
            roles = baseRoleListResponseData.getData();
        }

        //调用FeignClient查询菜单
        ResponseData<List<BaseModuleResources>> baseModuleResourceListResponseData = null;
       // if(null!=baseModuleResourceService.getMenusByUserId(baseUser.getId())){
            baseModuleResourceListResponseData = baseModuleResourceService.getMenusByUserId(baseUser.getId());
           // System.out.println("==== baseModuleResourceService.getMenusByUserId(baseUser.getId()=====");
//        }else{
//            baseModuleResourceListResponseData = baseModuleResourceService2.getMenusByUserId(baseUser.getId());
//            System.out.println("==== baseModuleResourceService2.getMenusByUserId(baseUser.getId()=====");
//        }

        System.out.println("====baseModuleResourceListResponseData:"+JsonUtils.deserializer(baseModuleResourceListResponseData));
        // 获取用户权限列表
        List<GrantedAuthority> authorities = convertToAuthorities(baseUser, roles);  //TODO

        // 存储菜单到redis
        if( ResponseCode.SUCCESS.getCode().equals(baseModuleResourceListResponseData.getCode()) && baseModuleResourceListResponseData.getData() != null){
            resourcesTemplate.delete(baseUser.getId() + "-menu");
            baseModuleResourceListResponseData.getData().forEach(e -> {
                resourcesTemplate.opsForList().leftPush(baseUser.getId() + "-menu", e);
            });
        }

        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user =  new org.springframework.security.core.userdetails.User(baseUser.getUserName(),
                baseUser.getPassword(), isActive(baseUser.getActive()), true, true, true, authorities);
        return new BaseUserDetail(baseUser, user);
    }

    private boolean isActive(int active){
        return active == 1 ? true : false;
    }

    /**
     * 当用户拥有一个角色或多个角色，返回资源权限
     * @param baseUser
     * @param roles
     * @return
     */
    private List<GrantedAuthority> convertToAuthorities(BaseUser baseUser, List<BaseRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList();
        // 清除 Redis 中用户的角色
        redisTemplate.delete(baseUser.getId());
        roles.forEach(e -> {
            // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleCode());
            authorities.add(authority);
            //存储角色到redis
            redisTemplate.opsForList().rightPush(baseUser.getId(), e);
        });
        return authorities;
    }
}
