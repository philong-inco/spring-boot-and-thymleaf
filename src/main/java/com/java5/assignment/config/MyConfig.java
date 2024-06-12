package com.java5.assignment.config;

import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyConfig {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomOauth2AuthenticationSuccessHandler customOauth2AuthenticationSuccessHandler;
    private final CustomOauth2UserService customOauth2UserService;

    public MyConfig(CustomAccessDeniedHandler customAccessDeniedHandler,
                    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                    @Lazy CustomOauth2UserService customOauth2UserService,
                    CustomOauth2AuthenticationSuccessHandler customOauth2AuthenticationSuccessHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customOauth2UserService = customOauth2UserService;
        this.customOauth2AuthenticationSuccessHandler = customOauth2AuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/category/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/color/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/size/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/product/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/product-detail/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/staff/**").hasRole("ADMIN")
                        .requestMatchers("/customer/", "/customer/add", "/customer/update/**", "/customer/detail/**", "/customer/delete/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/customer/modify", "/customer/profile/**").hasAnyRole("CUSTOMER", "OAUTH2_USER")
                        .requestMatchers("/role/**").hasRole("ADMIN")
                        .requestMatchers("/bill/create", "/bill/customer").permitAll()
                        .requestMatchers("/bill/manager/**").hasAnyRole("STAFF", "ADMIN")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oath2 -> oath2
                        .loginPage("/login")
                        .userInfoEndpoint()
                        .userService(customOauth2UserService)
                        .and()
                        .successHandler(customOauth2AuthenticationSuccessHandler)

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }


}
