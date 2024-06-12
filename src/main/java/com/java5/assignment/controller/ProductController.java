package com.java5.assignment.controller;

import com.java5.assignment.dto.request.ProductCreate;
import com.java5.assignment.dto.request.ProductUpdate;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.service.CategoryService;
import com.java5.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/product/")
public class ProductController {
    private ProductService service;
    private CategoryService categoryService;

    public ProductController(@Qualifier("productServiceImpl") ProductService service, @Qualifier("categoryServiceImpl") CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String getAll(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                         Model model) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền thực hiện hành động này");
        Page<ProductResponse> pageResult = service.getAll(page, size);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pageResult.getTotalPages());
        return "/product/list";
    }

    @GetMapping("add")
    public String getViewAdd(Model model) {
        model.addAttribute("add", new ProductCreate());
        model.addAttribute("category", categoryService.findByStatus(1));
        return "/product/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("add") @Valid ProductCreate create,
                      BindingResult result,
                      Model model) {
        if (service.existByName(create.getName().trim()))
            result.rejectValue("name", "name", "Name is exist");
        if (result.hasErrors()) {
            model.addAttribute("category", categoryService.findByStatus(1));
            return "/product/add";
        }
        service.add(create);
        return "redirect:/product/";
    }

    @GetMapping("update/{id}")
    public String getViewUpdate(Model model,
                                @PathVariable("id") Long id) {
        ProductResponse response = service.findById(id);
        ProductUpdate update = new ProductUpdate();
        update.setStatus(response.getStatus());
        update.setDescription(response.getDescription());
        update.setId(response.getId());
        update.setName(response.getName());
        update.setIdCategory(categoryService.findByName(response.getCategoryName()).getId());
        model.addAttribute("response", response);
        model.addAttribute("category", categoryService.findByStatus(1));
        model.addAttribute("update", update);
        return "/product/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("update") ProductUpdate update,
                         BindingResult result,
                         @ModelAttribute("response") ProductResponse response,
                         Model model) {
        if (service.existByNameAndDifferentId(update.getName(), update.getId()))
            result.rejectValue("name", "name", "Name is existed");
        if (result.hasErrors()) {
            model.addAttribute("category", categoryService.findByStatus(1));
            return "/product/update";
        }
        service.update(update);
        return "redirect:/product/";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("detail", service.findById(id));
        return "/product/detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        service.delete(id);
        return "redirect:/product/";
    }
}
