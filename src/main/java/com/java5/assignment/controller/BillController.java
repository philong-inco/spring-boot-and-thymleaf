package com.java5.assignment.controller;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.dto.request.BillAddressChange;
import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillDetailCreate;
import com.java5.assignment.dto.request.BillHistoryCreate;
import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.response.BillHistoryResponse;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.BillDetail;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.mapper.BillMapper;
import com.java5.assignment.repository.projection.BillDetailProjection;
import com.java5.assignment.repository.projection.BillProjection;
import com.java5.assignment.repository.projection.CartProjection;
import com.java5.assignment.repository.projection.ProductDetailProjection;
import com.java5.assignment.service.BillDetailService;
import com.java5.assignment.service.BillHistoryService;
import com.java5.assignment.service.BillService;
import com.java5.assignment.service.CartService;
import com.java5.assignment.service.CustomerService;
import com.java5.assignment.service.ProductDetailService;
import com.java5.assignment.service.StaffService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill/")
public class BillController {

    private CartService cartService;
    private CustomerService customerService;

    private StaffService staffService;

    private BillService billService;

    private HttpSession session;
    private BillDetailService billDetailService;

    private ProductDetailService productDetailService;

    private BillHistoryService billHistoryService;

    private BillMapper mapper;

    private GenerateCode generateCode;

    public BillController(GenerateCode generateCode, BillMapper mapper, BillHistoryService billHistoryService, HttpSession session, BillService billService, CartService cartService, CustomerService customerService, StaffService staffService, BillDetailService billDetailService, ProductDetailService productDetailService) {
        this.billService = billService;
        this.cartService = cartService;
        this.customerService = customerService;
        this.staffService = staffService;
        this.billDetailService = billDetailService;
        this.productDetailService = productDetailService;
        this.session = session;
        this.billHistoryService = billHistoryService;
        this.mapper = mapper;
        this.generateCode = generateCode;
    }

    @ModelAttribute("billStatus")
    public Map<Integer, String> billStatus() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Hóa đơn chờ");
        map.put(1, "Chờ xác nhận");
        map.put(2, "Chờ giao hàng");
        map.put(3, "Đang giao");
        map.put(4, "Giao lại");
        map.put(5, "Trả hàng/Hoàn tiền");
        map.put(6, "Hoàn thành");
        map.put(7, "Đã hủy");
        map.put(8, "Tất cả");
        return map;
    }

    @ModelAttribute("billStatusForUpdate")
    public Map<Integer, String> billStatusForUpdate() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Hóa đơn chờ");
        map.put(1, "Chờ xác nhận");
        map.put(2, "Chờ giao hàng");
        map.put(3, "Đang giao");
        map.put(4, "Giao lại");
        map.put(5, "Trả hàng/Hoàn tiền");
        map.put(6, "Hoàn thành");
        map.put(7, "Đã hủy");
        return map;
    }

    @GetMapping("manager/list")
    public String getAllForAdmin(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                                 Model model) {
        Page<BillProjection> pageResult = billService.getAllPage(page, size);
        model.addAttribute("countBillWaitComfirm", billService.countBillByStatus(1));
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("status", 8);
        return "bill/manager_list";
    }

    @GetMapping("manager/status/{st}")
    public String findByStatusForAdmin(@PathVariable("st") Integer status,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                                       Model model) {
        Page<BillProjection> pageResult = billService.findByStatusPage(page, size, status);
        model.addAttribute("countBillWaitComfirm", billService.countBillByStatus(1));
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("status", status);
        return "bill/manager_list";
    }

    @GetMapping("manager/comfirm/{id}")
    public String comfirmBill(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession sessionBill = request.getSession();
        Staff staff = (Staff) sessionBill.getAttribute("o");
        billService.changeStatus(id, 2, staff);
        BillHistoryCreate billHistoryCreate = BillHistoryCreate.builder()
                .staffId(staff.getId())
                .billId(id)
                .note(staff.getName() + " [" + staff.getUsername() + "] xác nhận bill")
                .build();
        billHistoryService.add(billHistoryCreate);
        return "redirect:/bill/manager/status/2";
    }

    @GetMapping("manager/change-status")
    public String changeStatus(@RequestParam("id") Long billId, @RequestParam("status") Integer status, HttpServletRequest request) {
        HttpSession sessionBill = request.getSession();
        Staff staff = (Staff) sessionBill.getAttribute("o");
        if (staff == null) return "redirect:/login";
        billService.changeStatus(status, billId);
        String nameStatus = billStatus().get(status);
        BillHistoryCreate billHistoryCreate = BillHistoryCreate.builder()
                .staffId(staff.getId())
                .billId(billId)
                .note(staff.getName() + " [" + staff.getUsername() + "] chuyển trạng thái thành " + nameStatus.toUpperCase())
                .build();
        billHistoryService.add(billHistoryCreate);

        // Call back amount product
        if (status == 5 || status == 7) {
            callBackAmountProduct(billId);
        }
        return "redirect:/bill/manager/list";
    }

    public void callBackAmountProduct(Long billId) {
        List<BillDetail> billDetail = billDetailService.getAllBillDetailByBillId(billId);
        for (BillDetail detail : billDetail) {
            productDetailService.upAmountById(detail.getProductDetail().getId(), detail.getAmount());
        }
    }

    @GetMapping("manager/create/step1")
    public String getManagerCreateStep1(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                                        @RequestParam(name = "key", required = false, defaultValue = "") String key,
                                        Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> pageResult = customerService.findByNameOrPhoneOrEmailOrCode(key, pageable);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        return "bill/manager_add_step1";
    }

    @PostMapping("manager/create/step1")
    public String postManagerCreateStep1(@RequestParam(name = "idCustomer", required = false) Long idCustomer, Model model, HttpServletRequest request) {
        if (idCustomer == null) {
            model.addAttribute("mess", "Hãy chọn khách hàng");
            return getManagerCreateStep1(0, 5, "", model);
        }
        HttpSession sessionTemp = request.getSession();
        sessionTemp.setAttribute("billCreate_Customer", idCustomer);
        return "redirect:/bill/manager/create/step2";
    }

    @GetMapping("manager/create/step2")
    public String getManagerCreateStep2(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                                        @RequestParam(name = "key", required = false, defaultValue = "") String key,
                                        Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetailProjection> pageResult = productDetailService.findByNameOrCodeOrColorOrSize(key, pageable);
        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        return "bill/manager_add_step2";
    }


    @PostMapping("manager/create/step2")
    public String postManagerCreateStep2(@RequestParam(name = "idProductDetail", required = false) List<Long> idProductDetail, Model model, HttpServletRequest request) {
        if (idProductDetail == null || idProductDetail.size() == 0) {
            model.addAttribute("mess", "Hãy chọn tối thiểu 1 sản phẩm");
            return getManagerCreateStep2(0, 5, "", model);
        }
        HttpSession sessionTemp = request.getSession();
        sessionTemp.setAttribute("billCreate_ProductDetail", idProductDetail);
        return "redirect:/bill/manager/create/step3"; // step 3
    }

    @GetMapping("manager/create/step3")
    public String getManagerCreateStep3(HttpServletRequest request, Model model) {
        HttpSession sessionTemp = request.getSession();
        Long idCustomer = (Long) sessionTemp.getAttribute("billCreate_Customer");
        List<Long> idProductDetail = (List<Long>) sessionTemp.getAttribute("billCreate_ProductDetail");
        CustomerResponse customer = customerService.findById(idCustomer);
        List<ProductDetailProjection> productDetailList = new ArrayList<>();
        for (Long idPd : idProductDetail) {
            productDetailList.add(productDetailService.findProjectionById(idPd));
        }
        model.addAttribute("customer", customer);
        model.addAttribute("list", productDetailList);
        return "bill/manager_add_step3";
    }

    @PostMapping("manager/create/step3")
    public String postManagerCreateStep3(@RequestParam Map<String, String> allParams,
                                         HttpServletRequest request, Model model) {
        HttpSession sessionTemp = request.getSession();
        Long idCustomer = (Long) sessionTemp.getAttribute("billCreate_Customer");
        Customer customer = customerService.findByIdEntity(idCustomer);
        Staff staff = (Staff) sessionTemp.getAttribute("o");
        String codeBill = "";
        do {
            codeBill = generateCode.generateCode(GenerateCode.BILL_PREFIX);
        } while (billService.existByCode(codeBill));
        Bill bill = Bill.builder()
                .status(6)
                .note("Mua tai quay")
                .customer(customer)
                .staff(staff)
                .code(codeBill)
                .build();

        boolean check = true;
        Integer productTotal = 0;
        BigDecimal moneyTotal = new BigDecimal(0.0f);
        List<BillDetail> billDetails = new ArrayList<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getValue().trim().equals("")) {
                check = false;
                model.addAttribute("mess", "Vui lòng điền đủ số lượng");
                break;
            }
            ProductDetail productDetail = productDetailService.findEntityById(Long.valueOf(entry.getKey()));
            Integer amountBill = Integer.valueOf(entry.getValue());
            productTotal += amountBill;
            moneyTotal = moneyTotal.add(productDetail.getPrice());
            if (productDetail.getAmount() < amountBill) {
                check = false;
                model.addAttribute("mess", "Số lượng không được quá sản phẩm tồn kho");
                break;
            }

            BillDetail bd = BillDetail.builder()
                    .productDetail(productDetail)
                    .amount(amountBill)
                    .price(productDetail.getPrice())
                    .status(0)
                    .build();
            billDetails.add(bd);
        }
        bill.setProductTotal(productTotal);
        bill.setMoneyTotal(moneyTotal);

        if (check) {
            Bill billEntity = billService.addEntity(bill);
            for (BillDetail bd : billDetails) {
                bd.setBill(billEntity);
                billDetailService.addEntity(bd);
                productDetailService.downAmountById(bd.getProductDetail().getId(), bd.getAmount());
            }
            BillHistoryCreate billHistoryCreate = BillHistoryCreate.builder()
                    .staffId(staff.getId())
                    .billId(billEntity.getId())
                    .note(staff.getName() + " [" + staff.getUsername() + "] tạo hóa đơn tại quầy")
                    .build();
            billHistoryService.add(billHistoryCreate);
            return "redirect:/bill/manager/status/6";
        }
        return getManagerCreateStep3(request, model);
    }

    @GetMapping("manager/update/{id}")
    public String getViewManagerUpdate(@PathVariable("id") Long id) {
        return "";
    }

    @PostMapping("manager/update/{id}")
    public String managerUpdate(@PathVariable("id") Long id) {
        return "";
    }

    @GetMapping("manager/detail/{id}")
    public String managerDetail(@PathVariable("id") Long id, Model model) {
        BillResponse response = billService.findResponseById(id);
        List<BillDetailProjection> billDetails = billDetailService.getBillDetailForClientByBillId(id);
        List<BillHistoryResponse> billHistorys = billHistoryService.getAllByBillId(id);
        model.addAttribute("bill", response);
        model.addAttribute("billDetail", billDetails);
        model.addAttribute("billHistory", billHistorys);
        model.addAttribute("addressChange", new BillAddressChange());
        return "/bill/manager_detail";
    }

    @PostMapping("manager/change-address/{id}")
    public String changeAdress(@ModelAttribute("addressChange") @Valid BillAddressChange change,
                               BindingResult result, @PathVariable("id") Long billId, Model model, HttpServletRequest request) {
        if (!change.getName().matches("^[a-zA-Z\\p{L}\\s]+$"))
            result.rejectValue("name", "name", "Tên không hợp lệ");
        if (!change.getPhone().matches("^[0-9]\\d*$"))
            result.rejectValue("phone", "phone", "SĐT không hợp lệ");
        if (result.hasErrors()) {
            BillResponse response = billService.findResponseById(billId);
            List<BillDetailProjection> billDetails = billDetailService.getBillDetailForClientByBillId(billId);
            List<BillHistoryResponse> billHistorys = billHistoryService.getAllByBillId(billId);
            model.addAttribute("bill", response);
            model.addAttribute("billDetail", billDetails);
            model.addAttribute("billHistory", billHistorys);
            return "/bill/manager_detail";
        }

        Bill resultBill = billService.changeAddress(billId, change);
        BillResponse response = billService.findResponseById(billId);
        List<BillDetailProjection> billDetails = billDetailService.getBillDetailForClientByBillId(billId);
        List<BillHistoryResponse> billHistorys = billHistoryService.getAllByBillId(billId);
        model.addAttribute("bill", response);
        model.addAttribute("billDetail", billDetails);
        model.addAttribute("billHistory", billHistorys);
        if (resultBill == null) {
            result.rejectValue("address", "address", "Thất bại!");
        } else {
            result.rejectValue("address", "address", "Thành công!");
            HttpSession sessionBill = request.getSession();
            Staff staff = (Staff) sessionBill.getAttribute("o");
            if (staff == null) return "redirect:/login";
            BillHistoryCreate billHistoryCreate = BillHistoryCreate.builder()
                    .staffId(staff.getId())
                    .billId(billId)
                    .note(staff.getName() + " [" + staff.getUsername() + "] cập nhật địa chỉ giao hàng")
                    .build();
            billHistoryService.add(billHistoryCreate);
        }

        return "/bill/manager_detail";
    }

    @GetMapping("customer")
    public String billForClient(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {
        Customer customerSession = (Customer) session.getAttribute("o");
        if (customerSession == null) {
            customerSession = getCustomer(request, response, customerSession);
        }
        model.addAttribute("list", billService.listBillForCustomer(customerSession.getId()));
        return "/bill/customer";
    }

    @GetMapping("customer/{id}")
    public String billDetailForClient(@PathVariable("id") Long billId, Model model) {
        model.addAttribute("bill", billService.findResponseById(billId));
        model.addAttribute("list", billDetailService.getBillDetailForClientByBillId(billId));
        model.addAttribute("billDetail", billHistoryService.getAllByBillId(billId));
        return "bill/customer_bill_detail";
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

    @PostMapping("create")
    public String create(HttpSession session, @RequestParam(name = "idCart", required = false) List<Long> selectCheckBox, Model model) {
        if (selectCheckBox == null) {
            selectCheckBox = (List<Long>) session.getAttribute("selectCheckBox");
            if (selectCheckBox == null || selectCheckBox.size() == 0) {
                return "redirect:/cart";
            }
        } else {
            session.setAttribute("selectCheckBox", selectCheckBox);
        }
        List<CartProjection> cartList = cartService.findByListIdCart(selectCheckBox);
        BigDecimal moneyTotal = moneyTotal(cartList);
        Integer productTotal = productTotal(cartList);
        model.addAttribute("list", cartList);
        model.addAttribute("moneyTotal", moneyTotal);
        model.addAttribute("productTotal", productTotal);
        Customer customer = (Customer) session.getAttribute("o");
        customer = customerService.findByIdEntity(customer.getId());
        CustomerUpdate update = CustomerUpdate.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .build();
        model.addAttribute("customerUpdate", update);
        model.addAttribute("selectCheckBox", selectCheckBox);
        return "/bill/comfirm";
    }

    @PostMapping("comfirm")
    public String comfirm(@ModelAttribute("customerUpdate") CustomerUpdate customerUpdate,
                          HttpSession session,
                          @RequestParam(name = "idCart", required = false) List<Long> selectCheckBox,
                          Model model) {
        if (selectCheckBox == null)
            selectCheckBox = (List<Long>) session.getAttribute("selectCheckBox");
        List<String> validateMess = new ArrayList<>();
        if (customerUpdate.getName().isBlank())
            validateMess.add("Name cannot blank");
        if (!customerUpdate.getName().matches("^[a-zA-Z\\p{L}\\s]+$"))
            validateMess.add("Name invalid");
        if (customerUpdate.getAddress().isBlank())
            validateMess.add("Address cannot blank");
        if (customerUpdate.getAddress().length() > 255)
            validateMess.add("Address too long");
        if (customerUpdate.getPhone().isBlank())
            validateMess.add("Phone cannot blank");
        if (!customerUpdate.getPhone().matches("^[0-9]\\d*$") || customerUpdate.getPhone().length() > 12 || customerUpdate.getPhone().length() < 10)
            validateMess.add("Phone invalid (10-12 character)");
        if (validateMess.size() > 0) {
            model.addAttribute("err", true);
            model.addAttribute("validateMess", validateMess);
            return create(session, null, model);
        }
        // thực hiện comfirm
        customerService.updateForCustomerGuest(customerUpdate);
        if (!validateBillBeforeCreate(selectCheckBox)) {
            validateMess.add("Đã có sự thay đổi về số lượng, vui lòng tải lại giỏ hàng");
            model.addAttribute("err", true);
            model.addAttribute("validateMess", validateMess);
            return create(session, null, model);
        }
        Bill bill = createdBill(selectCheckBox, customerUpdate);
        createBillDetail(bill, selectCheckBox);
        deleteCartFromList(selectCheckBox);
        return "redirect:/cart";
    }

    private void deleteCartFromList(List<Long> selectCheckBox) {
        for (Long idCart : selectCheckBox) {
            cartService.delete(idCart);
        }
    }

    private void createBillDetail(Bill bill, List<Long> selectCheckBox) {
        List<CartProjection> listCarts = cartService.findByListIdCart(selectCheckBox);
        for (CartProjection card : listCarts) {
            BillDetailCreate create = BillDetailCreate.builder()
                    .billId(bill.getId())
                    .productDetailId(card.getProductDetailId())
                    .amount(Integer.valueOf(card.getCartAmount()))
                    .price(new BigDecimal(card.getProductDetailPrice()))
                    .status(1)
                    .build();
            billDetailService.add(create);
            ProductDetail productDetail = productDetailService.findEntityById(card.getProductDetailId());
            productDetail.setAmount(productDetail.getAmount() - Integer.valueOf(card.getCartAmount()));
            productDetailService.updateEntity(productDetail);
        }
    }

    public Bill createdBill(List<Long> idCarts, CustomerUpdate customerUpdate) {
        // Tạo bản ghi HDCT, tạo hoa don, xoa san pham trong gio hang
        List<CartProjection> cartProjections = cartService.findByListIdCart(idCarts);
        Integer productTotal = productTotal(cartProjections);
        BigDecimal moneyTotal = moneyTotal(cartProjections);

        BillCreate create = BillCreate.builder()
                .customerId(customerUpdate.getId())
                .staffId(null)
                .status(1)
                .note(customerUpdate.getName() + " | " + customerUpdate.getPhone() + " | " + customerUpdate.getAddress())
                .moneyTotal(moneyTotal)
                .productTotal(productTotal)
                .build();
        BillResponse response = billService.add(create);
        Bill entity = billService.findEntityById(response.getId());
        return entity;
    }

    public boolean validateBillBeforeCreate(List<Long> cardIds) {
        List<CartProjection> cartList = cartService.findByListIdCart(cardIds);
        boolean check = true;
        for (CartProjection cart : cartList) {
            if (Integer.valueOf(cart.getCartAmount()) > Integer.valueOf(cart.getProductDetailAmount())) {
                check = false;
                break;
            }
        }
        return check;
    }


    public BigDecimal moneyTotal(List<CartProjection> list) {
        BigDecimal moneyTotal = new BigDecimal(0.0f);
        for (CartProjection cart : list) {
            BigDecimal a = new BigDecimal(cart.getCartAmount());
            BigDecimal b = new BigDecimal(cart.getProductDetailPrice());
            moneyTotal = moneyTotal.add(a.multiply(b));
        }
        return moneyTotal;
    }

    public Integer productTotal(List<CartProjection> list) {
        Integer productTotal = 0;
        for (CartProjection cart : list) {
            productTotal += Integer.valueOf(cart.getCartAmount());
        }
        return productTotal;
    }
}
