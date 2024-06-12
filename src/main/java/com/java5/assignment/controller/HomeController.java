package com.java5.assignment.controller;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@Controller
public class HomeController {
    @GetMapping("/access-denied")
    public String error(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                        Model model) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền");
        return "notauthor";
    }

    @GetMapping("/check-session")
    public String check(Model model, @SessionAttribute("o") Object o) {
        System.out.println(o.toString());
//        Object o = session.getAttribute("o");
//        if (o instanceof Staff){
//            Staff staff = (Staff) o;
//        }
//        if (o instanceof Customer){
//            Customer customer = (Customer) o;
//        }
//        model.addAttribute("o", o);
//        return "/categoty/list";
        Staff staff = null;
        Customer customer = null;
        model.addAttribute("s", null);
        model.addAttribute("c", null);
        if (o instanceof Staff) {
            staff = (Staff) o;
            model.addAttribute("s", staff);
        }
        if (o instanceof Customer) {
            customer = (Customer) o;
            model.addAttribute("c", customer);
        }


        return "o";
    }

    @GetMapping("/check-oauth2")
    @ResponseBody
    public String check1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return "Name: " + authentication.getName() + "\n" + "Role: " + authentication.getAuthorities().toString();
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
        return "Name: " + authentication.getName() + "\n" + "Email: " + email + "\n" + "Role: " + authentication.getAuthorities().toString();
    }

    @GetMapping("session-id")
    @ResponseBody
    public String sessionID(HttpSession session) {
        return "SessionID: " + session.getId();
    }
}
