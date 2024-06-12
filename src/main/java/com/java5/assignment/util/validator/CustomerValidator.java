package com.java5.assignment.util.validator;



import com.java5.assignment.entity.Customer;
import com.java5.assignment.service.CustomerService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CustomerValidator implements Validator {
    private CustomerService customerService;

    public CustomerValidator(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        CustomerCreate customerCreate = (CustomerCreate) target;
//        if (!customerCreate.getPassword().trim().equals(customerCreate.getPasswordComfirm().trim())){
//            errors.rejectValue("passwordComfirm", "passwordComfirm", "Password comfirm not matched");
//        }
//        if (customerService.findByEmail(customerCreate.getEmail()) != null){
//            errors.rejectValue("email", "email", "Email is existed");
//        }
    }
}
