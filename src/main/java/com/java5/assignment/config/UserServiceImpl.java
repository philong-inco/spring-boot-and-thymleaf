package com.java5.assignment.config;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.RoleRepository;
import com.java5.assignment.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private StaffRepository staffRepository;
    private RoleRepository roleRepository;

    private CustomerRepository customerRepository;

    public UserServiceImpl(StaffRepository staffRepository, RoleRepository roleRepository, CustomerRepository customerRepository) {
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Staff findByUsername(String username) {
        return staffRepository.findByUsername(username.trim()).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username.trim()).orElse(null);
        Staff staff = staffRepository.findByUsername(username.trim()).orElse(null);
        if (staff != null) {
            return User.builder()
                    .username(staff.getUsername())
                    .password(staff.getPassword())
                    .roles(staff.getRole().getName())
                    .build();
        } else if (customer != null) {
            return User.builder()
                    .username(customer.getEmail())
                    .password(customer.getPassword())
                    .roles(customer.getRole().getName())
                    .build();
        } else if (staff == null && customer == null)
            throw new UsernameNotFoundException("User not found");
        return null;
    }
}
