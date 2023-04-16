package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/api/auth")
@RestController
public class AuthorizeController {

    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";

    private final String USERNAME_REGEX = "^[a-zA-Z0-9_一-龥]+$";

    @Resource
    private AuthorizeService authorizeService;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern (regexp = EMAIL_REGEX)@RequestParam("email") String email,
                                          HttpSession session) {
        String result = authorizeService.sendValidateEmail(email, session.getId());
        return RestBean.success(result);

    }

    @PostMapping("/register")
    public RestBean<String> register(
            @Pattern(regexp = USERNAME_REGEX) @Length(min = 3, max = 16) @RequestParam("username") String username,
            @Length(min = 3, max = 16) @RequestParam("password") String password,
            @Pattern (regexp = EMAIL_REGEX) @RequestParam("email") String email,
            @Length(max = 6, min = 6) @RequestParam("code") String code) {
        String result = authorizeService.registerAccount(username, password, email, code);
        if (result == null) {
            return RestBean.failure(400, "内部错误");
        }
        return RestBean.success(result);
    }
}
