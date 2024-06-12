package com.java5.assignment.controller;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.FavoriteList;
import com.java5.assignment.entity.Product;
import com.java5.assignment.service.CustomerService;
import com.java5.assignment.service.FavoriteListService;
import com.java5.assignment.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite/")
public class FavoriteListController {

    private ProductService productService;
    private CustomerService customerService;
    private FavoriteListService favoriteListService;

    public FavoriteListController(ProductService productService, CustomerService customerService, FavoriteListService favoriteListService) {
        this.productService = productService;
        this.customerService = customerService;
        this.favoriteListService = favoriteListService;
    }

    @GetMapping("like")
    public ResponseEntity<String> like(@RequestParam("idSP") Long idSP, @RequestParam("idCu") Long idCustomer) {
        boolean check = true;
        Product product = productService.findEntityById(idSP);
        Customer customer = customerService.findByIdEntity(idCustomer);
        if (idSP == null || customer == null)
            check = false;
        if (product == null || customer == null)
            check = false;
        if (check) {
            if (!favoriteListService.existsByProductIdAndCustomerId(idSP, idCustomer)) {
                FavoriteList favoriteList = FavoriteList.builder()
                        .customer(customer)
                        .product(product)
                        .build();
                favoriteListService.add(favoriteList);
                return ResponseEntity.ok().body("Liked");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faild liked");
    }

    @GetMapping("dislike")
    public ResponseEntity<String> dislike(@RequestParam("idSP") Long idSP, @RequestParam("idCu") Long idCustomer) {
        boolean check = true;
        Product product = productService.findEntityById(idSP);
        Customer customer = customerService.findByIdEntity(idCustomer);
        if (idSP == null || customer == null)
            check = false;
        if (product == null || customer == null)
            check = false;
        if (check) {
            Long id = favoriteListService.findByProductIdAndCustomerId(idSP, idCustomer);
            if (id != null) {
                favoriteListService.delete(id);
                return ResponseEntity.ok().body("Disliked");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faild disliked");
    }



}
