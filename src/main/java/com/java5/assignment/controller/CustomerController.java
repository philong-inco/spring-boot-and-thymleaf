package com.java5.assignment.controller;

import com.java5.assignment.dto.request.CustomerCreate;
import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.service.CustomerService;
import com.java5.assignment.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.PropertyPermission;

@Controller
@RequestMapping("/customer/")
public class CustomerController {
    private CustomerService service;
    private RoleService roleService;

    public CustomerController(@Qualifier("customerServiceImpl") CustomerService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAll(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                         Model model) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền thực hiện hành động này");
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> pageResult = service.getAll(pageable);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pageResult.getTotalPages());
        return "/customer/list";
    }

    @GetMapping("add")
    public String getViewAdd(Model model) {
        model.addAttribute("roles", roleService.findByStatusList(1));
        model.addAttribute("add", new CustomerCreate());
        return "/customer/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("add") @Valid CustomerCreate create,
                      BindingResult result,
                      Model model) {
        if (!create.getPassword().equals(create.getPasswordComfirm()))
            result.rejectValue("passwordComfirm", "passwordComfirm", "Password comfirm not matched");
        if (service.existByEmail(create.getEmail()))
            result.rejectValue("email", "email", "Email is exist");
        if (service.existByPhone(create.getPhone()))
            result.rejectValue("phone", "phone", "Phone is exist");
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findByStatusList(1));
            return "/customer/add";
        }
        service.add(create);
        return "redirect:/customer/";
    }

    @GetMapping("update/{id}")
    public String getViewUpdate(Model model, @PathVariable("id") Long id) {
        CustomerResponse response = service.findById(id);
        CustomerUpdate update = new CustomerUpdate();
        update.setId(response.getId());
        update.setName(response.getName());
        update.setStatus(response.getStatus());
        update.setPhone(response.getPhone());
        update.setEmail(response.getEmail());
        model.addAttribute("roles", roleService.findByStatusList(1));
        model.addAttribute("response", response);
        model.addAttribute("update", update);
        return "/customer/update";
    }

    @PostMapping("update/{id}")
    public String update(@ModelAttribute("update") @Valid CustomerUpdate update,
                         BindingResult result,
                         @ModelAttribute("response") CustomerResponse response,
                         Model model) {
        if (service.existByEmailAndDifferentId(update.getEmail(), update.getId()))
            result.rejectValue("email", "email", "Email is exist");
        if (service.existByPhoneAndDifferentId(update.getPhone(), update.getId()))
            result.rejectValue("phone", "phone", "Phone is exist");
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findByStatusList(1));
            return "/customer/update";
        }
        service.update(update);
        return "redirect:/customer/";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("detail", service.findById(id));
        return "/customer/detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        service.delete(id);
        return "redirect:/customer/";
    }

    // for client

    @GetMapping("profile")
    public String profile(@SessionAttribute("o") Customer o, Model model) {
        CustomerResponse response = service.findById(o.getId());
        model.addAttribute("o", response);
        return "/customer/profile";
    }

    @GetMapping("modify")
    public String getViewModifyForClient(@SessionAttribute("o") Customer o, Model model) {
        CustomerResponse response = service.findById(o.getId());
        CustomerUpdate update = CustomerUpdate.builder()
                .status(response.getStatus())
                .phone(response.getPhone())
                .email(response.getEmail())
                .address(response.getAddress())
                .id(response.getId())
                .name(response.getName())
                .build();
        model.addAttribute("modify", update);
        return "/customer/modify";
    }

    @PostMapping("modify")
    public String modifyForClient(@ModelAttribute("modify") @Valid CustomerUpdate update, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/customer/modify";
        }
        CustomerResponse response = service.update(update);
        return "redirect:/customer/profile";
    }

    @GetMapping("register")
    public String getViewRegister(Model model) {
        CustomerCreate create = new CustomerCreate();
        create.setStatus(1);
        model.addAttribute("register", create);
        return "/customer/register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("register") @Valid CustomerCreate create,
                           BindingResult result, Model model) {
        if (!create.getPassword().equals(create.getPasswordComfirm()))
            result.rejectValue("passwordComfirm", "passwordComfirm", "Password comfirm not matched");
        if (service.existByEmail(create.getEmail()))
            result.rejectValue("email", "email", "Email is exist");
        if (service.existByPhone(create.getPhone()))
            result.rejectValue("phone", "phone", "Phone is exist");
        if (result.hasErrors()) {
            return "/customer/register";
        }
        create.setStatus(1);
        CustomerResponse response = service.add(create);
        model.addAttribute("mess", "Đăng ký thành công, hãy đăng nhập.");
        return "/login";
    }

}
