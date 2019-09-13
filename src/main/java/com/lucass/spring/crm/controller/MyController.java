package com.lucass.spring.crm.controller;

import java.util.List;

import com.lucass.spring.crm.model.Customer;
import com.lucass.spring.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
public class MyController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/showCustomers")
    public String findCustomers(Model model) {

        List<Customer> customers= (List<Customer>) customerService.findAll();

        model.addAttribute("customers", customers);

        return "showCustomers";
    }

//    @RequestMapping("/")
//    public String index()
//    {
//        List<Customer> customers= (List<Customer>) customerService.findAll();
//        String text = String.format("Hello I work in Spring Boot %d",customers.size());
//        return text;
//    }

}
