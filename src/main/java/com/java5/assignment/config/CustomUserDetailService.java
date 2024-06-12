package com.java5.assignment.config;

import com.java5.assignment.entity.Staff;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Staff> staff = staffRepository.findByUsername(username);

        if (staff.isPresent()){
            UserDetails userDetails = User.builder()
                    .username(staff.get().getUsername())
                    .password(staff.get().getPassword())
                    .roles(staff.get().getRole().getName())
                    .build();
            return userDetails;
        }

        throw new UsernameNotFoundException("Username not found");

    }
}
