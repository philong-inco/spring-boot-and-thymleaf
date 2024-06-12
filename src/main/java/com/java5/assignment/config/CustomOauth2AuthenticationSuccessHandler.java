package com.java5.assignment.config;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Role;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.RoleRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Component
public class CustomOauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final HttpServletRequest request;
    private final CustomerRepository customerRepository;

    private final RoleRepository roleRepository;

    public CustomOauth2AuthenticationSuccessHandler(RoleRepository roleRepository, HttpServletRequest request, CustomerRepository customerRepository) {
        this.request = request;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User user = oauthToken.getPrincipal();
            Map<String, Object> attributes = user.getAttributes();
            // Google specific attribute
            if (attributes.containsKey("email")) {
                email = (String) attributes.get("email");
            }
        }
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        Role role = roleRepository.findByName("CUSTOMER").orElse(null);
        if (customer == null){
            Customer newCustomer = Customer.builder()
                    .status(1)
                    .role(role)
                    .email(email).build();
            customer = customerRepository.save(newCustomer);
        }
        HttpSession session = request.getSession();
        session.setAttribute("o", customer);
        session.setAttribute("oauth2_email", email);

        response.sendRedirect("/home/products");

    }
}
