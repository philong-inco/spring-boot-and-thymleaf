package com.java5.assignment.controller;

import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Role;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.service.RoleService;
import com.java5.assignment.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.List;

@Controller
@RequestMapping("/staff/")
public class StaffController {
    private StaffService service;
    private RoleService roleService;

    public StaffController(@Qualifier("staffServiceImpl") StaffService service,
                           @Qualifier("roleServiceImpl") RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public String getAll(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                         Model model) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền thực hiện hành động này");
        Pageable pageable = PageRequest.of(page, size);
        Page<StaffResponse> pageResult = service.getAll(pageable);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pageResult.getTotalPages());
        return "/staff/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("add")
    public String getViewAdd(Model model) {
        model.addAttribute("add", new StaffCreate());
        model.addAttribute("roles", roleService.findByStatusList(1));
        return "/staff/add";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add")
    public String add(@ModelAttribute("add") @Valid StaffCreate create,
                      BindingResult result,
                      Model model) {
        if (service.isExistByUsername(create.getUsername()))
            result.rejectValue("username", "username", "Username is exist");
        if (!create.getPassword().trim().equals(create.getPasswordComfirm().trim()))
            result.rejectValue("passwordComfirm", "passwordComfirm", "Password comfirm not matches");
        if (service.existByEmail(create.getEmail()))
            result.rejectValue("email", "email", "Email is exist");
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findByStatusList(1));
            return "/staff/add";
        }
        service.add(create);
        return "redirect:/staff/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("update/{id}")
    public String getViewUpdate(@PathVariable("id") Long id,
                                Model model) {
        StaffResponse response = service.findById(id);
        StaffUpdate update = new StaffUpdate();
        update.setId(response.getId());
        update.setName(response.getName());
        update.setEmail(response.getEmail());
        update.setStatus(response.getStatus());
        update.setIdRole(roleService.findByName(response.getRole()).getId());
        model.addAttribute("response", response);
        model.addAttribute("update", update);
        model.addAttribute("roles", roleService.findByStatusList(1));
        return "/staff/update";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("update/{id}")
    public String update(@ModelAttribute("update") @Valid StaffUpdate update,
                         BindingResult result,
                         @ModelAttribute("response") StaffResponse response,
                         @PathVariable("id") Long id,
                         Model model) {
        if (service.existByEmailAndDifferentId(update.getEmail(), update.getId()))
            if (result.hasErrors()) {
                model.addAttribute("roles", roleService.findByStatusList(1));
                return "/staff/update";
            }
        service.update(update);
        return "redirect:/staff/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/staff/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("detail", service.findById(id));
        return "/staff/detail";
    }
}
