package com.java5.assignment.controller;

import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import com.java5.assignment.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/category/")
public class CategoryController {

    private CategoryService service;

    public CategoryController(@Qualifier("categoryServiceImpl") CategoryService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getAll(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                         Model model,
                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền thực hiện hành động này");
        Page<CategoryResponse> pageResult = service.getAll(page, size);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pageResult.getTotalPages());

        return "/category/list";
    }

    @GetMapping("status/{status}")
    public String findByStatus(Model model,
                               @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                               @PathVariable("status") Integer status) {
        model.addAttribute("list", service.findByStatus(page, size, status));
        return "/category/list";
    }

    // --------------ADD----------------
    @GetMapping("add")
    public String getViewAdd(Model model) {
        model.addAttribute("add", new CategoryCreate());
        return "/category/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("add") @Valid CategoryCreate categoryCreate,
                      BindingResult result) {
        if (service.existByName(categoryCreate.getName())) {
            result.rejectValue("name", "name", "Category name is exist");
        }
        if (result.hasErrors()) {
            return "/category/add";
        }
        service.add(categoryCreate);
        return "redirect:/category/";
    }

    // --------------UPDATE----------------
    @GetMapping("update/{id}")
    public String getViewUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("update", service.findById(id));
        return "/category/update";
    }

    @PostMapping("update/{id}")
    public String update(@ModelAttribute("update") @Valid CategoryResponse response,
                         BindingResult result,
                         @PathVariable("id") Long id) {
        if (service.existByNameAndDifferentId(response.getName(), response.getId())) {
            result.rejectValue("name", "name", "Category name is exist");
        }
        if (result.hasErrors()) {
            return "/category/update";
        }
        service.update(response);
        return "redirect:/category/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/category/";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", service.findById(id));
        return "category/detail";
    }


}
