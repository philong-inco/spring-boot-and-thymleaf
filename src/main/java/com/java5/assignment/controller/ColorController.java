package com.java5.assignment.controller;

import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.service.ColorService;
import jakarta.validation.Valid;
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
@RequestMapping("/color/")
public class ColorController {
    private ColorService service;

    public ColorController(ColorService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getAll(@RequestParam(name = "error", required = false, defaultValue = "") String error,
                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                         Model model) {
        if (error.equalsIgnoreCase("true"))
            model.addAttribute("error", "Bạn không đủ quyền thực hiện hành động này");
        Page<ColorResponse> pageResult = service.getAll(page, size);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pageResult.getTotalPages());
        return "/color/list";
    }

    @GetMapping("add")
    public String getViewAdd(Model model) {
        model.addAttribute("add", new ColorCreate());
        return "/color/add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("add") @Valid ColorCreate colorCreate,
                      BindingResult result,
                      Model model) {
        if (service.existByName(colorCreate.getName()))
            result.rejectValue("name", "name", "Name is exist");
        if (result.hasErrors()) {
            return "/color/add";
        }
        service.add(colorCreate);
        return "redirect:/color/";
    }

    @GetMapping("update/{id}")
    public String getViewUpdate(@PathVariable("id") Long id,
                                Model model) {
        ColorResponse response = service.findById(id);
        ColorUpdate update = new ColorUpdate();
        update.setId(response.getId());
        update.setName(response.getName());
        update.setStatus(response.getStatus());
        model.addAttribute("response", response);
        model.addAttribute("update", update);
        return "/color/update";
    }

    @PostMapping("update/{id}")
    public String update(@ModelAttribute("response") ColorResponse response,
                         @ModelAttribute("update") @Valid ColorUpdate colorUpdate,
                         BindingResult result,
                         @PathVariable("id") Long id,
                         Model model) {
        if (service.existByNameAndDifferentId(colorUpdate.getName(), colorUpdate.getId()))
            result.rejectValue("name", "name", "Name is exist");
        if (result.hasErrors()) {
            return "/color/update";
        }
        service.update(colorUpdate);
        return "redirect:/color/";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("detail", service.findById(id));
        return "/color/detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/color/";
    }

}
