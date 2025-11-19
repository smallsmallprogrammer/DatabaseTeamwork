package com.example.poverty.controller;

import com.example.poverty.model.SysUser;
import com.example.poverty.repository.SysUserRepository;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.Authentication; // 新增这行导入

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final SysUserRepository userRepo;

    public UserController(SysUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<SysUser> listUsers() {
        return userRepo.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }
   
    @GetMapping("/current")
    public SysUserDTO getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        SysUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        return new SysUserDTO(
            user.getUserId(), // 这里使用 getUserId() 而非 getId()
            user.getUsername(),
            user.getFullname(),
            user.getEmail(),
            //user.getRole().getRoleName(), // 补充角色名称的正确获取方式
            LocalDateTime.now() // 注意：原 SysUser 模型没有 registerTime 字段，此处需根据实际情况调整
        );
    }

    // DTO 调整为匹配实体类字段
    public record SysUserDTO(
        Long userId, // 对应实体类的 userId
        String username,
        String fullname,
        String email,
        //String roleName,
        LocalDateTime registerTime
    ) {}

}