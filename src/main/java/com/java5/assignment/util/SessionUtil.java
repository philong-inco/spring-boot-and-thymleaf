package com.java5.assignment.util;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static void addUserToSession(HttpServletRequest request, Object o){
        HttpSession session = request.getSession(false);
        if (o instanceof Customer){
            Customer customer = (Customer) o;
            session.setAttribute("o", customer);
        }
        if (o instanceof Staff){
            Staff staff = (Staff) o;
            session.setAttribute("o", staff);
        }

    }
}
