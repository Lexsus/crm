package com.lucass.spring.crm.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.lucass.spring.crm.model.Customer;
import com.lucass.spring.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RestController
public class MyController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "customers/createOrUpdateCustomerForm";
    @Autowired
    private CustomerRepository customers;

    @GetMapping("/showCustomers")
    public String findCustomers(Model model) {

        List<Customer> customers= (List<Customer>) this.customers.findAll();

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
            return "redirect:/customers/" + customer.getId();
        }
    }
    //    @RequestMapping("/")
//    public String index()
//    {
//        List<Customer> customers= (List<Customer>) customers.findAll();
//        String text = String.format("Hello I work in Spring Boot %d",customers.size());
//        return text;
//    }
    @GetMapping("/customers")
    public String processFindForm(Customer customer, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (customer.getName() == null) {
            customer.setName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Customer> results = this.customers.findByName(customer.getName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("name", "notFound", "not found");
            return "customers/findCustomers";
        } else if (results.size() == 1) {
            // 1 owner found
            customer = results.iterator().next();
            return "redirect:/customers/" + customer.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "customers/customersList";
        }
    }

    @GetMapping("/customers/{customerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("customerId") int customerId, Model model) {
        Customer owner = this.customers.findById(customerId);
        model.addAttribute(owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/customers/{customerId}/edit")
    public String processUpdateOwnerForm(@Valid Customer customer, BindingResult result, @PathVariable("customerId") int customerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            customer.setId(customerId);
            this.customers.save(customer);
            return "redirect:/customers/{customerId}";
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param customerId the ID of the customer to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/customers/{customerId}")
    public ModelAndView showOwner(@PathVariable("customerId") int customerId) {
        ModelAndView mav = new ModelAndView("customers/customerDetails");
        mav.addObject(this.customers.findById(customerId));
        return mav;
    }

}
