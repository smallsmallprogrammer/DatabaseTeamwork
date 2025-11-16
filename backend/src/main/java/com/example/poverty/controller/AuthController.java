package com.example.poverty.controller;

import com.example.poverty.model.SysUser;
import com.example.poverty.model.Role;
import com.example.poverty.repository.SysUserRepository;
import com.example.poverty.repository.RoleRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SysUserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 使用 SecretKey 代替普通字符串，保证 HS256 安全
    private final SecretKey jwtSecret = Keys.hmacShaKeyFor("0123456789abcdef0123456789abcdef".getBytes());
    private final long expirationMs = 24 * 60 * 60 * 1000; // 1天

    public AuthController(SysUserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // ------------------------
    // 登录
    // ------------------------
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest req) {
        SysUser user = userRepo.findByUsername(req.username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!encoder.matches(req.password, user.getPasswordHash())) {
            throw new RuntimeException("密码错误");
        }

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();

        return new LoginResponse(token, user.getUsername());
    }

    // ------------------------
    // 注册
    // ------------------------
    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest req) {
        if (userRepo.findByUsername(req.username).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }

        Role role = roleRepo.findById(req.roleId)
                .orElseThrow(() -> new RuntimeException("角色不存在"));

        SysUser user = SysUser.builder()
                .username(req.username)
                .passwordHash(encoder.encode(req.password))
                .role(role)
                .fullname(req.fullname)
                .email(req.email)
                .build();

        userRepo.save(user);

        // 注册后直接返回 token
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();

        return new LoginResponse(token, user.getUsername());
    }

    // DTO
    public record LoginRequest(String username, String password) {}
    public record RegisterRequest(String username, String password, Long roleId, String fullname, String email) {}
    public record LoginResponse(String token, String username) {}
}
