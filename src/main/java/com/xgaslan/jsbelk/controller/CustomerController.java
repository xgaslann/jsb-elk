package com.xgaslan.jsbelk.controller;

import com.xgaslan.jsbelk.config.RestApis;
import com.xgaslan.jsbelk.domain.Customer;
import com.xgaslan.jsbelk.dto.request.CustomerSaveRequest;
import com.xgaslan.jsbelk.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(RestApis.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody CustomerSaveRequest request) {
        return customerService.save(request);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable String id, @RequestBody CustomerSaveRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        return "Customer deleted successfully!";
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/search")
    public List<Customer> searchCustomers(@RequestParam String q) {
        return customerService.searchCustomers(q);
    }

    @GetMapping("/search/name/{name}")
    public List<Customer> findByName(@PathVariable String name) {
        return customerService.findByName(name);
    }

    @GetMapping("/search/lastname/{lastName}")
    public List<Customer> findByLastName(@PathVariable String lastName) {
        return customerService.findByLastName(lastName);
    }

    @GetMapping("/search/fullname")
    public List<Customer> findByFullName(@RequestParam String name, @RequestParam String lastName) {
        return customerService.findByNameAndLastName(name, lastName);
    }

    @GetMapping("/search/name-like/{name}")
    public List<Customer> findByNameLike(@PathVariable String name) {
        return customerService.findAllByNameLike(name);
    }

    @GetMapping("/search/fuzzy/{searchTerm}")
    public List<Customer> fuzzySearch(@PathVariable String searchTerm) {
        return customerService.findByNameOrLastNameFuzzy(searchTerm);
    }

    @GetMapping("/age/{age}")
    public List<Customer> findByAge(@PathVariable Integer age) {
        return customerService.findByAge(age);
    }

    @GetMapping("/age/greater-than/{age}")
    public List<Customer> findOlderThan(@PathVariable Integer age) {
        return customerService.findByAgeGreaterThan(age);
    }

    @GetMapping("/age/between")
    public List<Customer> findByAgeRange(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return customerService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/active")
    public List<Customer> findActiveCustomers() {
        return customerService.findActiveCustomers();
    }

    @GetMapping("/inactive")
    public List<Customer> findInactiveCustomers() {
        return customerService.findInactiveCustomers();
    }

    @PostMapping("/{id}/activate")
    public String activateCustomer(@PathVariable String id) {
        customerService.activateCustomer(id);
        return "Customer activated successfully!";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateCustomer(@PathVariable String id) {
        customerService.deactivateCustomer(id);
        return "Customer deactivated successfully!";
    }

    @GetMapping("/active/age-range")
    public List<Customer> findActiveInAgeRange(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return customerService.findActiveCustomersInAgeRange(minAge, maxAge);
    }

    @GetMapping("/search/name-and-age")
    public List<Customer> findByNameAndAge(@RequestParam String name, @RequestParam Integer age) {
        return customerService.findByNameContainingAndAgeGreaterThan(name, age);
    }

    @GetMapping("/stats")
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", customerService.count());
        stats.put("active", customerService.countActiveCustomers());
        stats.put("inactive", customerService.countInactiveCustomers());
        return stats;
    }

    @GetMapping("/count/active")
    public long countActiveCustomers() {
        return customerService.countActiveCustomers();
    }

    @GetMapping("/count/age-range")
    public long countByAgeRange(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return customerService.countByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable String id) {
        return customerService.existsById(id);
    }

    @GetMapping("/exists/fullname")
    public boolean existsByFullName(@RequestParam String name, @RequestParam String lastName) {
        return customerService.existsByFullName(name, lastName);
    }

    @DeleteMapping("/inactive")
    public String deleteInactiveCustomers() {
        customerService.deleteInactiveCustomers();
        return "All inactive customers deleted!";
    }

    @DeleteMapping("/older-than/{age}")
    public List<Customer> deleteOldCustomers(@PathVariable Integer age) {
        return customerService.deleteOldCustomers(age);
    }

    @GetMapping("/active/paged")
    public Page<Customer> getActiveCustomersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(direction), sort));

        return customerService.findActiveCustomersPaged(pageable);
    }
}
