package com.java5.assignment.config;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.StaffRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private final CustomerRepository customerRepository;

    private final StaffRepository staffRepository;

    public CustomAuthenticationSuccessHandler(CustomerRepository customerRepository, StaffRepository staffRepository) {
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String name = authentication.getName();
        Staff staff = staffRepository.findByUsername(name).orElse(null);
        Customer customer = customerRepository.findByEmail(name).orElse(null);
        HttpSession session = request.getSession();
        if (staff != null) {
            session.setAttribute("o", staff);
            response.sendRedirect("/category/");
        } else if (customer != null) {
            session.setAttribute("o", customer);
            response.sendRedirect("/home/products");
        }

    }
}
