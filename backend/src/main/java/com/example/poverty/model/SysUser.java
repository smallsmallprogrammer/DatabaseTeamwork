package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sys_user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SysUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private String fullname;
    private String email;

    // Explicit getters/setters to avoid Lombok dependency
    public Long getUserId() { return this.userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return this.passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Role getRole() { return this.role; }
    public void setRole(Role role) { this.role = role; }

    public String getFullname() { return this.fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    // Minimal builder replacement for SysUser.builder()
    public static SysUserBuilder builder() {
        return new SysUserBuilder();
    }

    public static class SysUserBuilder {
        private Long userId;
        private String username;
        private String passwordHash;
        private Role role;
        private String fullname;
        private String email;

        public SysUserBuilder userId(Long userId) { this.userId = userId; return this; }
        public SysUserBuilder username(String username) { this.username = username; return this; }
        public SysUserBuilder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public SysUserBuilder role(Role role) { this.role = role; return this; }
        public SysUserBuilder fullname(String fullname) { this.fullname = fullname; return this; }
        public SysUserBuilder email(String email) { this.email = email; return this; }

        public SysUser build() {
            SysUser u = new SysUser();
            u.setUserId(this.userId);
            u.setUsername(this.username);
            u.setPasswordHash(this.passwordHash);
            u.setRole(this.role);
            u.setFullname(this.fullname);
            u.setEmail(this.email);
            return u;
        }
    }
}