package com.example.service.impl;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MailSender mailSender;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${spring.mail.username}")
    private String mailFrom;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null)
            throw new UsernameNotFoundException("用户名不能为空!");
        Account account = userMapper.findAccountByNameOrEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码不正确!");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    /**
     * 发送验证码到指定邮箱
     * @param email
     * @return
     */
    @Override
    public String sendValidateEmail(String email, String sessionId) {
        String key = "email:" + email;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(stringRedisTemplate.getExpire(key)).orElse(0L);
            if (expire > 120) {
                return "不可以频繁发送信息";
            }
        }
        Account account = userMapper.findAccountByNameOrEmail(email);
        if (account != null) {
            return "该邮箱已被注册!";
        }
        // 1. 生成对应的验证码
        Random random = new Random(47);
        int code = random.nextInt(900000) + 100000;
        // 2. 发送验证码
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是: " + code);
        try {
            mailSender.send(message);
            // 3. 存到 Redis 中

            stringRedisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            System.out.println(stringRedisTemplate.opsForValue().get(key));
            return "验证码发送成功";
        } catch (MailException e) {
            e.printStackTrace();
        }

        return "验证码发送失败，请联系管理员";
    }

    @Override
    public String registerAccount(String username, String password, String email, String code) {
        String msg = "";
        String key = "email:" + email;
        if (code == null)
            return "验证码不能为空";
        String redisCode = stringRedisTemplate.opsForValue().get(key);
        if (redisCode == null)
            return "验证码过期了";
        if (!redisCode.equals(code))
            return "请输入正确的验证码";
        password = encoder.encode(password);
        int result = userMapper.createAccount(username, password, email);
        if (!(result > 0))
            return "注册失败，内部原因，请联系管理员";
        return "注册成功，快去登陆吧";
    }
}
