package com.ssarylog.api.config;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserPrincipal extends User {
    private final Long userId;

    // role: 역할 -> 관리자, 사용자, 매니저
    // authority: 권한 -> 글쓰기, 글 읽기, 사용자정지시키기

    public UserPrincipal(com.ssarylog.api.domain.User user) {
        super(user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ADMIN")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }

}
