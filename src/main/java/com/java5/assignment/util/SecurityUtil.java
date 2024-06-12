package com.java5.assignment.util;

import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private CustomerRepository customerRepository;
    private HttpServletRequest request;
    public void setSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String email = oidcUser.getEmail();
            HttpSession session = request.getSession();
            session.setAttribute("o", customerRepository.findByEmail(email).orElse(null));
//        if (staff != null) {
//            session.setAttribute("o", staff);
//        } else if (customer != null) {
//            session.setAttribute("o", customer);
//        }
//        response.sendRedirect("/category/");

        }
    }
}
