package com.example.TracNghiem.services;

import com.example.TracNghiem.Role;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.repository.IUserRepository;
import com.example.TracNghiem.repository.IRoleRepository;
import com.example.TracNghiem.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserRepository user1Repository;

    @Autowired
    private IRoleRepository roleRepository;

    // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.
    public void save(@NotNull User user) {
        // Mã hóa mật khẩu
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} saved successfully.", user.getUsername());
    }

    public  List<User> getAllUser(){
        return  userRepository.findAll();
    }

    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
    public void setDefaultRole(String username) {
        userRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                    userRepository.save(user);
                    log.info("Default role assigned to user {}.", username);
                },
                () -> {
                    log.warn("User {} not found for role assignment.", username);
                    throw new UsernameNotFoundException("User not found");
                }
        );
    }

    // Tải thông tin chi tiết người dùng để xác thực.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return user1Repository.findById(id);
    }
    public User findById(Long id) {
        return user1Repository.findById(id).orElse(null);
    }
    public void deleteById(Long id) {
        user1Repository.deleteById(id);
    }


    public User updateUser(@NotNull User user){
        User existingUser = userRepository.findById(String.valueOf(user.getId()))
                .orElseThrow(() -> new IllegalStateException("Id user  " +
                        user.getId() + " khong ton tai."));
        existingUser.setEmail(user.getEmail());
        existingUser.setFullname(user.getFullname());
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }

    public User getUserLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return null;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(null);
    }
}


