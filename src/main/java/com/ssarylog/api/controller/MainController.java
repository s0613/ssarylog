package com.ssarylog.api.controller;

import com.ssarylog.api.config.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String main() {
        return "mainPage";
    }
    // Spring Enable(preAuthorize = true) default true PostAuthorize -> 응답값에 대한 권한
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "사용자 페이지입니다.";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "관리자 페이집입니다. ";
    }
}
