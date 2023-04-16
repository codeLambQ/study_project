package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
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

    @Resource
    private AuthorizeService authorizeService;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern (regexp = EMAIL_REGEX)@RequestParam("email") String email,
                                          HttpSession session) {
        if (authorizeService.sendValidateEmail(email, session.getId())) {
            return RestBean.success("验证码发送成功，请注意查收");
        } else {
            return RestBean.failure(400, "验证码发送失败，请联系管理员!");
        }
    }
}
