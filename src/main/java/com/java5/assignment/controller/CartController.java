package com.java5.assignment.controller;

import com.java5.assignment.dto.request.CartCreate;
import com.java5.assignment.dto.request.CartUpdate;
import com.java5.assignment.entity.Cart;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.mapper.CartMapper;
import com.java5.assignment.repository.projection.CartProjection;
import com.java5.assignment.service.CartService;
import com.java5.assignment.service.CustomerService;
import com.java5.assignment.service.ProductDetailService;
import com.java5.assignment.util.SessionUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private CustomerService customerService;

    private ProductDetailService productDetailService;

    private CartMapper mapper;

    private HttpSession session;
    private Customer customer = null;

    public CartController(CartMapper mapper, CartService cartService, CustomerService customerService, HttpSession session, ProductDetailService productDetailService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.productDetailService = productDetailService;
        this.session = session;
        this.mapper = mapper;
    }

    @GetMapping("")
    public String getAll(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
        Customer customerSession = (Customer) session.getAttribute("o");
        if (customerSession == null) {
            customerSession = getCustomer(request, response, customerSession);
        }
        List<CartProjection> list = cartService.getAll(customerSession.getId());
        SessionUtil.addUserToSession(request, customerSession);
        model.addAttribute("list", list);
        model.addAttribute("customerSession", customerSession);
        return "/cart/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        cartService.delete(id);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> update(@RequestParam("id") Long id,
                                         @RequestParam("quantity") Integer quantity) {
        try {

            Cart cart = cartService.findById(id);
            if (cart == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
            }
            if ((cart.getProductDetail().getAmount() < quantity) || quantity < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
            }
            CartUpdate update = CartUpdate.builder()
                    .id(cart.getId())
                    .amount(quantity)
                    .customerId(cart.getCustomer().getId())
                    .productDetailId(cart.getProductDetail().getId())
                    .build();
            cartService.update(update);
            return ResponseEntity.ok("Update successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
        }
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseEntity<String> add(@RequestParam("id") Long idProductDetail, @RequestParam("amount") Integer amount, @RequestParam("idCustomer") Long idCustomer) {
        if (idCustomer == null || idProductDetail == null || amount == null || amount == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add to cart failed");
        }
        if (productDetailService.findEntityById(idProductDetail) == null
                || customerService.findByIdEntity(idCustomer) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add to cart failed");
        }
        if (cartService.existByProductDetailId(idCustomer, idProductDetail)) {
            // update
            Cart cart = cartService.findByCustomerIdAndProductDetailId(idCustomer, idProductDetail);
            CartUpdate cartUpdate = CartUpdate.builder()
                    .amount(amount)
                    .build();
            cart = mapper.updateToEntity(cartUpdate, cart);
            cartService.updateEntity(cart);
            return ResponseEntity.ok().body("add successfully");
        } else {
            // add
            CartCreate cartCreate = CartCreate.builder()
                    .amount(amount)
                    .customerId(idCustomer)
                    .productDetailId(idProductDetail)
                    .build();
            cartService.add(cartCreate);
            return ResponseEntity.ok().body("add successfully");
        }
    }

    public Customer getCustomer(HttpServletRequest request, HttpServletResponse response, Customer customerSession) {
        String sessionId = getSessionIdFromCookie(request);
        if (sessionId == null) {
            addCookieToClient(response);
            sessionId = this.session.getId();
        }
        customerSession = customerService.findBySessionId(sessionId);
        if (customerSession == null) {
            customerSession = customerService.createCustomerWithSessionId(sessionId);
        }
        return customerSession;
    }

    private void addCookieToClient(HttpServletResponse response) {
        Cookie cookie = new Cookie("SESSION_CUSTOMER_ID", this.session.getId());
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private String getSessionIdFromCookie(HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie c : cookie) {
                if ("SESSION_CUSTOMER_ID".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }


}
