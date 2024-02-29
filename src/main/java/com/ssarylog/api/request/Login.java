package com.ssarylog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    @NotBlank(message = "plz email")
    private String email;
    @NotBlank(message = "plz password")
    private String password;
    @Builder
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
