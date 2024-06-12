package com.java5.assignment.controller;

import com.java5.assignment.dto.request.ProductDetailCreate;
import com.java5.assignment.dto.request.ProductDetailUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.ProductDetailResponse;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.repository.projection.ProductDetailProjection;
import com.java5.assignment.service.ColorService;
import com.java5.assignment.service.ProductDetailService;
import com.java5.assignment.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/product-detail/")
public class ProductDetailController {

    private ProductDetailService service;
    private ColorService colorService;
    private SizeService sizeService;

    public ProductDetailController(ProductDetailService service, ColorService colorService, SizeService sizeService) {
        this.service = service;
        this.colorService = colorService;
        this.sizeService = sizeService;
    }

    @ModelAttribute("listColor")
    public List<ColorResponse> listColor() {
        return colorService.findByStatusList(1);
    }

    @ModelAttribute("listSize")
    public List<SizeResponse> listSize() {
        return sizeService.findByStatusList(1);
    }

    @GetMapping("list/{idSP}")
    public String getAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                         @PathVariable("idSP") Long idSP, Model model) {
        Page<ProductDetailProjection> resultPage = service.getAll(page, size, idSP);
        model.addAttribute("idSP", idSP);
        model.addAttribute("list", resultPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", resultPage.getTotalPages());
        model.addAttribute("add", new ProductDetailCreate());
        return "/product-detail/list";
    }

    @PostMapping("add/{idSP}")
    public String add(@ModelAttribute("add") @Valid ProductDetailCreate create,
                      BindingResult result,
                      @PathVariable("idSP") Long idSP,
                      Model model) {
        if (service.checkExistForCreate(create.getIdColor(), create.getIdSize(), idSP))
            result.rejectValue("idSize", "idSize", "Biến thể đã tồn tại");
        if (!create.getPrice().equals("")) {
            if (!create.getPrice().matches("^\\d+(\\.\\d+)?$")) {
                result.rejectValue("price", "price", "Giá không hợp lệ");

            } else if (Float.valueOf(create.getPrice()) < 0) {
                result.rejectValue("price", "price", "Giá không hợp lệ");
            }
        }
        if (!create.getAmount().equals("")) {
            if (!create.getAmount().matches("^[0-9]\\d*$")) {
                result.rejectValue("amount", "amount", "Số lượng không hợp lệ");
            } else if (Integer.valueOf(create.getAmount()) < 0) {
                result.rejectValue("amount", "amount", "Số lượng không hợp lệ");
            }
        }


        if (result.hasErrors()) {
            Page<ProductDetailProjection> resultPage = service.getAll(0, 10, idSP);
            model.addAttribute("list", resultPage.getContent());
            return "/product-detail/list";
        }
        create.setIdProduct(idSP);
        service.add(create);
        return "redirect:/product-detail/list/" + idSP;
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") Long id, @RequestParam("idSP") Long idSP) {
        service.delete(id);
        return "redirect:/product-detail/list/" + idSP;
    }

    @GetMapping("detail")
    public String detail(@RequestParam("id") Long id, @RequestParam("idSP") Long idSP, Model model) {
        model.addAttribute("detail", service.findById(id));
        model.addAttribute("idSP", idSP);
        return "/product-detail/detail";
    }

    @GetMapping("update")
    public String update(@RequestParam("id") Long id, @RequestParam("idSP") Long idSP, Model model) {
        ProductDetail entity = service.findEntityById(id);
        ProductDetailUpdate update = ProductDetailUpdate.builder()
                .idProduct(entity.getProduct().getId())
                .image(entity.getImage())
                .status(entity.getStatus())
                .amount(entity.getAmount() + "")
                .price(entity.getPrice() + "")
                .idSize(entity.getSize().getId())
                .idColor(entity.getColor().getId())
                .id(entity.getId())
                .build();
        model.addAttribute("update", update);
        return "/product-detail/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("update") @Valid ProductDetailUpdate update,
                         BindingResult result,
                         @RequestParam("id") Long id, @RequestParam("idSP") Long idSP, Model model) {
        if (service.checkExistForUpdate(update.getIdColor(), update.getIdSize(), update.getIdProduct(), update.getId()))
            result.rejectValue("status", "status", "Biến the da ton tai");
        if (!update.getPrice().equals("")) {
            if (!update.getPrice().matches("^\\d+(\\.\\d+)?$")) {
                result.rejectValue("price", "price", "Giá không hợp lệ");

            } else if (Float.valueOf(update.getPrice()) < 0) {
                result.rejectValue("price", "price", "Giá không hợp lệ");
            }
        }
        if (!update.getAmount().equals("")) {
            if (!update.getAmount().matches("^[0-9]\\d*$")) {
                result.rejectValue("amount", "amount", "Số lượng không hợp lệ");
            } else if (Integer.valueOf(update.getAmount()) < 0) {
                result.rejectValue("amount", "amount", "Số lượng không hợp lệ");
            }
        }
        if (result.hasErrors()) {
            return "/product-detail/update";
        }
        service.update(update);
        return "redirect:/product-detail/list/" + idSP;
    }


}
