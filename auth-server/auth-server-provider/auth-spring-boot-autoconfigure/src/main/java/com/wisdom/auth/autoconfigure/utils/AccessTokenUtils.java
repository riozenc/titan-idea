package com.wisdom.auth.autoconfigure.utils;

//import com.wisdom.auth.api.pojo.Constant;
//import com.wisdom.auth.data.api.mapper.model.MenuInfo;
//import com.wisdom.auth.data.api.mapper.model.RoleInfo;
//import com.wisdom.auth.data.api.mapper.model.UserInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
//import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by yxs on 2019/1/29.
 */
public class AccessTokenUtils {

//    @Autowired
//    private TokenStore tokenStore;

//    @Autowired
//    private HttpServletRequest request;
//
//    @Autowired
//    private TokenExtractor tokenExtractor;
//
//    @Autowired
//    private RedisTemplate<String, RoleInfo> baseRoleTemplate;
//
//    @Autowired
//    private RedisTemplate<String, MenuInfo> baseModelTemplate;

    /**
     * 从token获取用户信息
     * @return
     */
//    public UserInfo getUserInfo(){
//        return (UserInfo) getAccessToken().getAdditionalInformation().get(Constant.USER_INFO);
//    }
//
//    public List<RoleInfo> getRoleInfo(){
//        String userId = getUserInfo().getId().toString();
//        long size = baseRoleTemplate.opsForList().size(userId);
//        return baseRoleTemplate.opsForList().range(userId, 0, size);
//    }
//
//    public List<MenuInfo> getMenuInfo(){
//        String key = getUserInfo().getId().toString() + "-menu";
//        long size = baseModelTemplate.opsForList().size(key);
//        return baseModelTemplate.opsForList().range(key, 0, size);
//    }

//    private OAuth2AccessToken getAccessToken() throws AccessDeniedException {
//        OAuth2AccessToken token;
//        // 抽取token
//        Authentication a = tokenExtractor.extract(request);
//        try {
//            System.out.println("----------------------getAccessToken="+tokenStore+"----------------------------------------");
//            System.out.println("----------------------getPrincipal="+a+"----------------------------------------");
//            // 调用JwtAccessTokenConverter的extractAccessToken方法解析token
//            token = tokenStore.readAccessToken((String) a.getPrincipal());
//            System.out.println("---------------------------"+token.getValue()+"-----------------------------------");
//        } catch(Exception e) {
//            throw new AccessDeniedException("AccessToken Not Found.");
//        }
//        return token;
//    }
}
