package com.java5.assignment.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class MyExceptionHandler {
//    @ExceptionHandler(RuntimeException.class)
//    public String runtimeExceptionHandler(RuntimeException e, Model model){
//        model.addAttribute(e.getMessage());
//        return "index";
//    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public String accessDeniedExceptionHandler(AccessDeniedException accessDeniedException, RedirectAttributes redirectAttributes){
//        redirectAttributes.addAttribute("errorMess", "Bạn không đủ quyền");
//        return "redirect:/category/";
//    }
}
