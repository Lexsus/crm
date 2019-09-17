package com.lucass.spring.crm.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.lucass.spring.crm.model.Customer;
import com.lucass.spring.crm.repository.CustomerRepository;
import com.lucass.spring.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
public class MyController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "customers/createOrUpdateCustomerForm";
    @Autowired
    private CustomerRepository customerService;

    @GetMapping("/showCustomers")
    public String findCustomers(Model model) {

        List<Customer> customers= (List<Customer>) customerService.findAll();

        model.addAttribute("customers", customers);

        return "showCustomers";
    }

    @GetMapping("/customers/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("customer", new Customer());
        return "customers/findCustomers";
    }

    @GetMapping("/customers/new")
    public String initCreationForm(Map<String, Object> model) {
        Customer customer = new Customer();
        model.put("customer", customer);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/customers/new")
    public String processCreationForm(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            return "redirect:/owners/" + customer.getId();
        }
    }
    //    @RequestMapping("/")
//    public String index()
//    {
//        List<Customer> customers= (List<Customer>) customerService.findAll();
//        String text = String.format("Hello I work in Spring Boot %d",customers.size());
//        return text;
//    }
    @GetMapping("/owners")
    public String processFindForm(Customer owner, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getName() == null) {
            owner.setName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Customer> results = this.customerService.findByName(owner.getName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "owners/ownersList";
        }
    }

}
