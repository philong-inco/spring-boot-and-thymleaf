package com.java5.assignment.config;

import com.java5.assignment.entity.Staff;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public interface UserService extends UserDetailsService {
    Staff findByUsername(String username);
}
