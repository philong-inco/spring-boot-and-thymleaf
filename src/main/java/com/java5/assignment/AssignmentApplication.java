package com.java5.assignment;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.entity.*;
import com.java5.assignment.repository.BillDetailRepository;
import com.java5.assignment.repository.BillHistoryRepository;
import com.java5.assignment.repository.BillRepository;
import com.java5.assignment.repository.CartRepository;
import com.java5.assignment.repository.CategoryRepository;
import com.java5.assignment.repository.ColorRepository;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.FavoriteListRepository;
import com.java5.assignment.repository.ProductDetailRepository;
import com.java5.assignment.repository.ProductRepository;
import com.java5.assignment.repository.RoleRepository;
import com.java5.assignment.repository.SizeRepository;
import com.java5.assignment.repository.StaffRepository;
import com.java5.assignment.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder,
                                               SizeRepository sizeService,
                                               ColorRepository colorService,
                                               StaffRepository staffService,
                                               RoleRepository roleService,
                                               ProductRepository productService,
                                               ProductDetailRepository productDetailService,
                                               FavoriteListRepository favoriteListService,
                                               CustomerRepository customerService,
                                               CategoryRepository categoryService,
                                               CartRepository cartService,
                                               BillRepository billService,
                                               BillDetailRepository billDetailService,
                                               BillHistoryRepository billHistoryService,
                                               GenerateCode generateCode
    ) {
        return r -> {
//            initDatabase(passwordEncoder,
//            sizeService,
//                    colorService,
//                    staffService,
//                    roleService,
//                    productService,
//                    productDetailService,
//                    favoriteListService,
//                    customerService,
//                    categoryService,
//                    cartService,
//                    billService,
//                    billDetailService,
//                    billHistoryService,
//                    generateCode);
        };
    }

    private void initDatabase(PasswordEncoder passwordEncoder, SizeRepository sizeService, ColorRepository colorService, StaffRepository staffService,
                              RoleRepository roleService, ProductRepository productService,
                              ProductDetailRepository productDetailService, FavoriteListRepository favoriteListService,
                              CustomerRepository customerService, CategoryRepository categoryService,
                              CartRepository cartService, BillRepository billService, BillDetailRepository billDetailService,
                              BillHistoryRepository billHistoryService, GenerateCode generateCode) {
        // Table Size
        Size size1 = Size.builder()
                .code(generateCode.generateCode(GenerateCode.SIZE_PREFIX))
                .name("S")
                .status(1)
                .build();
        Size size2 = Size.builder()
                .code(generateCode.generateCode(GenerateCode.SIZE_PREFIX))
                .name("M")
                .status(1)
                .build();
        Size size3 = Size.builder()
                .code(generateCode.generateCode(GenerateCode.SIZE_PREFIX))
                .name("L")
                .status(1)
                .build();
        Size size4 = Size.builder()
                .code(generateCode.generateCode(GenerateCode.SIZE_PREFIX))
                .name("XL")
                .status(1)
                .build();
        Size size5 = Size.builder()
                .code(generateCode.generateCode(GenerateCode.SIZE_PREFIX))
                .name("XXL")
                .status(1)
                .build();
        sizeService.save(size1);
        sizeService.save(size2);
        sizeService.save(size3);
        sizeService.save(size4);
        sizeService.save(size5);

        // Table Color
        Color color1 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("Red")
                .status(1)
                .build();
        Color color2 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("Blue")
                .status(1)
                .build();
        Color color3 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("Green")
                .status(1)
                .build();
        Color color4 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("Yellow")
                .status(1)
                .build();
        Color color5 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("Gray")
                .status(1)
                .build();
        Color color6 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("Black")
                .status(1)
                .build();
        Color color7 = Color.builder()
                .code(generateCode.generateCode(GenerateCode.COLOR_PREFIX))
                .name("White")
                .status(1)
                .build();
        colorService.save(color1);
        colorService.save(color2);
        colorService.save(color3);
        colorService.save(color4);
        colorService.save(color5);
        colorService.save(color6);
        colorService.save(color7);

        // Table Role
        Role role1 = Role.builder()
                .name("ADMIN")
                .status(1)
                .build();
        Role role2 = Role.builder()
                .name("STAFF")
                .status(1)
                .build();
        Role role3 = Role.builder()
                .name("CUSTOMER")
                .status(1)
                .build();

        roleService.save(role1);
        roleService.save(role2);
        roleService.save(role3);


        // Table Staff
        Staff staff1 = Staff.builder()
                .email("philong.inco@gmail.com")
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .name("I'm admin")
                .status(1)
                .role(roleService.findByName("ADMIN").orElse(null))
                .build();
        Staff staff2 = Staff.builder()
                .email("nhanvien@abc.com")
                .username("staff")
                .password(passwordEncoder.encode("staff"))
                .name("I'm nhan vien")
                .status(1)
                .role(roleService.findByName("STAFF").orElse(null))
                .build();

        staffService.save(staff1);
        staffService.save(staff2);
//
//
//        // Table Customer
        Customer customer1 = Customer.builder()
                .code(generateCode.generateCode(GenerateCode.CUSTOMER_PREFIX))
                .name("Khach hang 1")
                .email("khachhang1@gmail.com")
                .password(passwordEncoder.encode("123"))
                .phone("0981212323")
                .role(roleService.findByName("CUSTOMER").orElse(null))
                .status(1)
                .build();
        Customer customer2 = Customer.builder()
                .code(generateCode.generateCode(GenerateCode.CUSTOMER_PREFIX))
                .name("Khach hang 2")
                .email("khachhang2@gmail.com")
                .password(passwordEncoder.encode("123"))
                .phone("09834544321")
                .role(roleService.findByName("CUSTOMER").orElse(null))
                .status(1)
                .build();
        Customer customer3 = Customer.builder()
                .code(generateCode.generateCode(GenerateCode.CUSTOMER_PREFIX))
                .name("Khach hang 3")
                .email("khachhang3@gmail.com")
                .password(passwordEncoder.encode("123"))
                .phone("09265334321")
                .role(roleService.findByName("CUSTOMER").orElse(null))
                .status(1)
                .build();
        Customer customer4 = Customer.builder()
                .code(generateCode.generateCode(GenerateCode.CUSTOMER_PREFIX))
                .name("Khach hang 4")
                .email("khachhang4@gmail.com")
                .password(passwordEncoder.encode("123"))
                .phone("0985666321")
                .role(roleService.findByName("CUSTOMER").orElse(null))
                .status(1)
                .build();

        customerService.save(customer1);
        customerService.save(customer2);
        customerService.save(customer3);
        customerService.save(customer4);


        // Table category
        Category category1 = Category.builder()
                .code(generateCode.generateCode(GenerateCode.CATEGORY_PREFIX))
                .name("Category 1")
                .status(1)
                .build();
        Category category2 = Category.builder()
                .code(generateCode.generateCode(GenerateCode.CATEGORY_PREFIX))
                .name("Category 2")
                .status(1)
                .build();
        Category category3 = Category.builder()
                .code(generateCode.generateCode(GenerateCode.CATEGORY_PREFIX))
                .name("Category 3")
                .status(1)
                .build();

        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);

        // Table product
        Product product1 = Product.builder()
                .code(generateCode.generateCode(GenerateCode.PRODUCT_PREFIX))
                .name("San Pham 1")
                .description("Mo ta 1")
                .category(categoryService.findByName("Category 1"))
                .status(1)
                .build();
        Product product2 = Product.builder()
                .code(generateCode.generateCode(GenerateCode.PRODUCT_PREFIX))
                .name("San Pham 2")
                .description("Mo ta 2")
                .category(categoryService.findByName("Category 2"))
                .status(1)
                .build();
        Product product3 = Product.builder()
                .code(generateCode.generateCode(GenerateCode.PRODUCT_PREFIX))
                .name("San Pham 3")
                .description("Mo ta 3")
                .category(categoryService.findByName("Category 3"))
                .status(1)
                .build();

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);

//        // Table product detail
//        ProductDetail productDetail1 = ProductDetail.builder()
//
//                .build();
    }


}
