package com.java5.assignment.config;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Role;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.RoleRepository;
import com.java5.assignment.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(CustomOauth2UserService.class);
    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private HttpServletRequest request;

    private GenerateCode generateCode;
    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    public CustomOauth2UserService(CustomerRepository customerRepository, RoleRepository roleRepository, GenerateCode generateCode, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.generateCode = generateCode;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    @Lazy
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Role roleCustomer = roleRepository.findByName("CUSTOMER").orElse(null);

        Customer customer = customerRepository.findByEmail(email).orElseGet(() -> {
            Customer newCustomer = Customer.builder()
                    .code(generateCode.generateCode(GenerateCode.CUSTOMER_PREFIX))
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode("root"))
                    .status(1)
                    .role(roleCustomer)
                    .build();
            return newCustomer;
        });
        Customer temp = customerRepository.save(customer);
//        UserDetails userDetails = User.builder()
//                .username(temp.getEmail())
//                .password(temp.getPassword())
//                .authorities(new SimpleGrantedAuthority(temp.getRole().getName()))
//                .build();

//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
//        DefaultOAuth2User principal = new DefaultOAuth2User(authorities, attributes, "email");
//
//        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(principal, authorities, userRequest.getClientRegistration().getRegistrationId());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


//        return principal;
        return oAuth2User;
    }
}
