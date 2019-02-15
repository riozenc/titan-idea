package com.wisdom.auth.provider.config.auth.pojo;

import com.wisdom.auth.data.api.mapper.model.UserInfo;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by yxs on 2019/1/22.
 * 包装org.springframework.security.core.userdetails.User类
 * 新增 userInfo 用于生成 jwt 的用户信息
 */
public class BaseUserDetail implements UserDetails, CredentialsContainer {

    private final UserInfo userInfo;
    private final org.springframework.security.core.userdetails.User user;

    public BaseUserDetail(UserInfo userInfo, User user) {
        this.userInfo = userInfo;
        this.user = user;
    }


    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
