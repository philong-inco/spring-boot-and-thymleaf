package com.java5.assignment.controller;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.repository.ProductDetailRepository;
import com.java5.assignment.repository.ProductRepository;
import com.java5.assignment.repository.projection.ProductDetailProjection;
import com.java5.assignment.repository.projection.ProductForHomeProjecttion;
import com.java5.assignment.service.CustomerService;
import com.java5.assignment.service.ProductDetailService;
import com.java5.assignment.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home/")
public class MainController {
    private ProductService productService;
    private HttpSession session;
    private CustomerService customerService;

    private ProductDetailService productDetailService;

    @Autowired
    private ProductRepository repository;

    public MainController(ProductDetailService productDetailService, ProductService productService, HttpSession session, CustomerService customerService) {
        this.productService = productService;
        this.session = session;
        this.customerService = customerService;
        this.productDetailService = productDetailService;
    }

    @GetMapping("product-detail/{id}")
    public String getProductDetail(@PathVariable("id") Long idProduct, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Customer customerSession = (Customer) session.getAttribute("o");
        if (customerSession == null) {
            customerSession = getCustomer(request, response, customerSession);
        }
        ProductForHomeProjecttion product = productService.getProductSingleForClient(customerSession.getId(), idProduct);
        List<ProductDetailProjection> details = productDetailService.findByIdProductForClient(idProduct);
        model.addAttribute("product", product);
        model.addAttribute("details", details);
        model.addAttribute("idCustomer", customerSession.getId());
        return "/product/home_product_detail";
    }

    @GetMapping("products")
    public String getAllForClient(@RequestParam(name = "key", required = false, defaultValue = "") String key,
                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(name = "size", required = false, defaultValue = "9") int size,
                                  Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Customer customerSession = (Customer) session.getAttribute("o");
        if (customerSession == null) {
            customerSession = getCustomer(request, response, customerSession);
        }
        Long idCustomer = customerSession.getId();
        Page<ProductForHomeProjecttion> pageResult = productService.getProductForClient(page, size, key, idCustomer);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("idCustomer", customerSession.getId());
        return "/product/home_product_list";
    }

    @GetMapping("favorite")
    public String favorite(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                           @RequestParam(name = "size", required = false, defaultValue = "9") int size,
                           Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Customer customerSession = (Customer) session.getAttribute("o");
        if (customerSession == null) {
            customerSession = getCustomer(request, response, customerSession);
        }
        Long idCustomer = customerSession.getId();
        Page<ProductForHomeProjecttion> pageResult = productService.getProductFavorite(page, size, idCustomer);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("idCustomer", customerSession.getId());
        return "/favorite/list";
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
