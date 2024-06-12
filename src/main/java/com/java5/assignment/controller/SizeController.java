package com.java5.assignment.controller;

import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.service.ColorService;
import com.java5.assignment.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
@RequestMapping("/size/")
public class SizeController {
    private SizeService service;

    public SizeController(@Qualifier("sizeServiceImpl") SizeService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getAll(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                         Model model) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền thực hiện hành động này");

        Page<SizeResponse> pageResult = service.getAll(page, size);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pageResult.getTotalPages());
        return "/size/list";
    }

    @GetMapping("add")
    public String getViewAdd(Model model) {
        model.addAttribute("add", new SizeCreate());
        return "/size/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("add") @Valid SizeCreate sizeCreate,
                      BindingResult result,
                      Model model) {
        if (service.existByName(sizeCreate.getName()))
            result.rejectValue("name", "name", "Name is exist");
        if (result.hasErrors()) {
            return "/size/add";
        }
        service.add(sizeCreate);
        return "redirect:/size/";
    }

    @GetMapping("update/{id}")
    public String getViewUpdate(@PathVariable("id") Long id,
                                Model model) {
        SizeResponse response = service.findById(id);
        SizeUpdate update = new SizeUpdate();
        update.setId(response.getId());
        update.setName(response.getName());
        update.setStatus(response.getStatus());
        model.addAttribute("response", response);
        model.addAttribute("update", update);
        return "/size/update";
    }

    @PostMapping("update/{id}")
    public String update(@ModelAttribute("response") SizeResponse response,
                         @ModelAttribute("update") @Valid SizeUpdate sizeUpdate,
                         BindingResult result,
                         @PathVariable("id") Long id,
                         Model model) {
        if (service.existByNameAndDifferentId(sizeUpdate.getName(), sizeUpdate.getId()))
            result.rejectValue("name", "name", "Name is exist");
        if (result.hasErrors()) {
            return "/size/update";
        }
        service.update(sizeUpdate);
        return "redirect:/size/";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("detail", service.findById(id));
        return "/size/detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/size/";
    }
}
