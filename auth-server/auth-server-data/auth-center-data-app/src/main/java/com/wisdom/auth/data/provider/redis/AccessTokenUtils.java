package com.wisdom.auth.data.provider.redis;

import com.wisdom.auth.common.constant.Constant;
import com.wisdom.auth.autoconfigure.config.AccessTokenConfiguration;
import com.wisdom.auth.common.utils.JsonUtils;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;
import com.wisdom.auth.data.api.mapper.model.RoleInfo;
import com.wisdom.auth.data.api.mapper.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


/**
 * Created by yxs on 2019/1/29.
 */
@Service
public class AccessTokenUtils {

//    @Autowired
//    private TokenStore tokenStore;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    AccessTokenConfiguration accessTokenConfiguration;

    @Autowired
    private RedisTemplate<String, RoleInfo> baseRoleTemplate;

    @Autowired
    private RedisTemplate<String, MenuInfo> baseModelTemplate;

    /**
     * 从token获取用户信息
     * @return
     */
    public UserInfo getUserInfo(){
        String token = this.extractHeaderToken(request);
        if (token==null){
            token = request.getParameter("access_token");
            if (token == null) {
                return null;
            } else {
                request.setAttribute("ACCESS_TOKEN_TYPE", "Bearer");
            }
        }
        String content = accessTokenConfiguration.extract(token);
        Map map = JsonUtils.serializable(content, Map.class);
        String json = JsonUtils.deserializer(map.get(Constant.USER_INFO));
        UserInfo userInfo = JsonUtils.serializable(json, UserInfo.class);
        return userInfo;
    }

    public List<RoleInfo> getRoleInfo(){
        String userId = getUserInfo().getId().toString();
        long size = baseRoleTemplate.opsForList().size(userId);
        return baseRoleTemplate.opsForList().range(userId, 0, size);
    }

    public List<MenuInfo> getMenuInfo(){
        String key = getUserInfo().getId().toString() + "-menu";
        long size = baseModelTemplate.opsForList().size(key);
        return baseModelTemplate.opsForList().range(key, 0, size);
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders("Authorization");

        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }

            value = (String)headers.nextElement();
        } while(!value.toLowerCase().startsWith("Bearer".toLowerCase()));

        return value;
    }
}
