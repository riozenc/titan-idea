package com.wisdom.auth.autoconfigure.config;

import com.wisdom.auth.autoconfigure.utils.AccessTokenUtils;
import com.wisdom.auth.data.api.mapper.model.MenuInfo;
import com.wisdom.auth.data.api.mapper.model.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yxs on 2019/1/29.
 */
@ConfigurationProperties(prefix = "security")
public class AccessDecisionManagerIml  implements AccessDecisionManager {

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    private AntPathMatcher matcher = new AntPathMatcher();

    private String[] ignoreds;

    @Value("${spring.application.name}")
    private String applicationName;

    private String url;

    private String httpMethod;


    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 请求路径
        url = getUrl(o);
        // http 方法
        httpMethod = getMethod(o);

        // 不拦截的请求
        for(String path : ignoreds){
            String temp = path.trim();
            if (matcher.match(temp, url)) {
                return;
            }
        }
        return;
        // URL 鉴权
//        Iterator<RoleInfo> iterator = accessTokenUtils.getRoleInfo().iterator();
//        while (iterator.hasNext())
//        {   RoleInfo roleInfo = iterator.next();
//            if (roleInfo.getModules().size() > 0 && checkSubModule(roleInfo.getModules())) {
//                return;
//            }
//        }
//
//        throw new AccessDeniedException("无权限！");

    }

    /**
     *  获取请求中的url
     */
    private String getUrl(Object o) {
        //获取当前访问url
        String url = ((FilterInvocation)o).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            return url.substring(0, firstQuestionMarkIndex);
        }
        return url;
    }

    private String getMethod(Object o) {
        return ((FilterInvocation)o).getRequest().getMethod();
    }

    // 检查子模块权限
    private boolean checkSubModule(List<MenuInfo> modules) {

        Iterator<MenuInfo> iterator = modules.iterator();
        while (iterator.hasNext())
        {
            MenuInfo e = iterator.next();
            System.out.println("-------------------------------applicationName:"+applicationName+"  e.getProjectName():"+e.getProjectName());

            // 匹配当前应用的资源
            if(applicationName.equals(e.getProjectName())) {
                System.out.println("-------------------------------e.getIsLeaf():"+e.getIsLeaf()+"  e.getStatus():"+e.getStatus()+" e.getMenuUrl():"+e.getMenuUrl());

                if (e.getIsLeaf() == 1 && e.getStatus() == 1 && e.getMenuUrl() != null && !"".equals(e.getMenuUrl())) {

                    System.out.println("-------------------------------返回 matchUrl:"+matchUrl(url, e.getMenuUrl()));//+" httpMethod:"+e.getHttpMethod().toUpperCase()+" "+httpMethod.toUpperCase());
                    if (matchUrl(url, e.getMenuUrl())) {// && httpMethod.toUpperCase().equals(e.getHttpMethod().toUpperCase())
                        return true;
                    }
                }

                // 递归检查子模块的权限
                if (e.getSubModules().size() > 0) {
                    if (checkSubModule(e.getSubModules())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean matchUrl(String url, String modulePath) {
        System.out.println("-------------------------------url:"+url+" modulePath:"+modulePath);
        List urls = Arrays.asList(url.split("/")).stream().filter(e -> !"".equals(e)).collect(Collectors.toList());
        Collections.reverse(urls);

        List paths = Arrays.asList(modulePath.split("/")).stream().filter(e -> !"".equals(e)).collect(Collectors.toList());
        Collections.reverse(paths);

        // 如果数量不相等
        if (urls.size() != paths.size()) {
            return false;
        }

        for(int i = 0; i < paths.size(); i++){
            // 如果是 PathVariable 则忽略
            String item = (String) paths.get(i);
            if (item.charAt(0) != '{' && item.charAt(item.length() - 1) != '}') {
                // 如果有不等于的，则代表 URL 不匹配
                if (!item.equals(urls.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public void setIgnored(String ignored) {
        if(ignored != null && !"".equals(ignored))
            this.ignoreds = ignored.split(",");
        else
            this.ignoreds = new String[]{};
    }
}
