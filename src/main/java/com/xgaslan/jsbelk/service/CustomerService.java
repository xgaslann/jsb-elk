package com.xgaslan.jsbelk.service;

import com.xgaslan.jsbelk.domain.Customer;
import com.xgaslan.jsbelk.dto.request.CustomerSaveRequest;
import com.xgaslan.jsbelk.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer save(CustomerSaveRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .fullName(request.getName() + " " + request.getLastName())
                .age(request.getAge())
                .isActive(true)
                .build();

        log.info("Saving customer: {} {}", customer.getName(), customer.getLastName());
        return customerRepository.save(customer);
    }

    public Optional<Customer> findById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(this::calculateFullName);
        return customer;
    }

    public List<Customer> findAll() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public void deleteById(String id) {
        log.info("Deleting customer with id: {}", id);
        customerRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return customerRepository.existsById(id);
    }

    public long count() {
        return customerRepository.count();
    }

    private void calculateFullName(Customer customer) {
        if (customer.getName() != null && customer.getLastName() != null) {
            customer.setFullName(customer.getName() + " " + customer.getLastName());
        }
    }

    public List<Customer> findByName(String name) {
        List<Customer> customers = customerRepository.findByName(name);
        customers.forEach(this::calculateFullName);
        log.info("Found {} customers with name: {}", customers.size(), name);
        return customers;
    }

    public List<Customer> findByLastName(String lastName) {
        List<Customer> customers = customerRepository.findByLastName(lastName);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findAllByNameLike(String name) {
        List<Customer> customers = customerRepository.findAllByNameLike(name);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameStartingWith(String prefix) {
        List<Customer> customers = customerRepository.findByNameStartingWith(prefix);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameEndingWith(String suffix) {
        List<Customer> customers = customerRepository.findByNameEndingWith(suffix);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameContainingIgnoreCase(String name) {
        List<Customer> customers = customerRepository.findByNameContainingIgnoreCase(name);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByLastNameContaining(String lastName) {
        List<Customer> customers = customerRepository.findByLastNameContaining(lastName);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameAndLastName(String name, String lastName) {
        List<Customer> customers = customerRepository.findByNameAndLastName(name, lastName);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameOrLastName(String name, String lastName) {
        List<Customer> customers = customerRepository.findByNameOrLastName(name, lastName);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameContainingAndLastNameContaining(String name, String lastName) {
        List<Customer> customers = customerRepository.findByNameContainingAndLastNameContaining(name, lastName);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAge(Integer age) {
        List<Customer> customers = customerRepository.findByAge(age);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAgeGreaterThan(Integer age) {
        List<Customer> customers = customerRepository.findByAgeGreaterThan(age);
        customers.forEach(this::calculateFullName);
        log.info("Found {} customers older than {}", customers.size(), age);
        return customers;
    }

    public List<Customer> findByAgeLessThan(Integer age) {
        List<Customer> customers = customerRepository.findByAgeLessThan(age);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAgeBetween(Integer minAge, Integer maxAge) {
        List<Customer> customers = customerRepository.findByAgeBetween(minAge, maxAge);
        customers.forEach(this::calculateFullName);
        log.info("Found {} customers between ages {} and {}", customers.size(), minAge, maxAge);
        return customers;
    }

    public List<Customer> findByAgeGreaterThanEqual(Integer age) {
        List<Customer> customers = customerRepository.findByAgeGreaterThanEqual(age);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAgeLessThanEqual(Integer age) {
        List<Customer> customers = customerRepository.findByAgeLessThanEqual(age);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAgeIn(List<Integer> ages) {
        List<Customer> customers = customerRepository.findByAgeIn(ages);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAgeNotNull() {
        List<Customer> customers = customerRepository.findByAgeNotNull();
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByAgeIsNull() {
        List<Customer> customers = customerRepository.findByAgeIsNull();
        customers.forEach(this::calculateFullName);
        return customers;
    }

    // ===== ACTIVE STATUS QUERIES =====

    public List<Customer> findActiveCustomers() {
        List<Customer> customers = customerRepository.findByIsActiveTrue();
        customers.forEach(this::calculateFullName);
        log.info("Found {} active customers", customers.size());
        return customers;
    }

    public List<Customer> findInactiveCustomers() {
        List<Customer> customers = customerRepository.findByIsActiveFalse();
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByIsActive(Boolean isActive) {
        List<Customer> customers = customerRepository.findByIsActive(isActive);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByIsActiveIsNull() {
        List<Customer> customers = customerRepository.findByIsActiveIsNull();
        customers.forEach(this::calculateFullName);
        return customers;
    }

    // ===== COMPLEX COMBINATIONS =====

    public List<Customer> findByNameContainingAndAgeGreaterThan(String name, Integer age) {
        List<Customer> customers = customerRepository.findByNameContainingAndAgeGreaterThan(name, age);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findActiveCustomersInAgeRange(Integer minAge, Integer maxAge) {
        List<Customer> customers = customerRepository.findByIsActiveTrueAndAgeBetween(minAge, maxAge);
        customers.forEach(this::calculateFullName);
        log.info("Found {} active customers between ages {} and {}", customers.size(), minAge, maxAge);
        return customers;
    }

    public List<Customer> findByNameOrLastNameAndIsActiveTrue(String name, String lastName) {
        List<Customer> customers = customerRepository.findByNameOrLastNameAndIsActiveTrue(name, lastName);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findOldActiveCustomersSorted(Integer age) {
        List<Customer> customers = customerRepository.findByAgeGreaterThanAndIsActiveTrueOrderByNameAsc(age);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public long countActiveCustomers() {
        return customerRepository.countByIsActive(true);
    }

    public long countInactiveCustomers() {
        return customerRepository.countByIsActive(false);
    }

    public long countByAgeBetween(Integer minAge, Integer maxAge) {
        return customerRepository.countByAgeBetween(minAge, maxAge);
    }

    public boolean existsByFullName(String name, String lastName) {
        return customerRepository.existsByNameAndLastName(name, lastName);
    }

    public boolean existsActiveCustomerOlderThan(Integer age) {
        return customerRepository.existsByIsActiveTrueAndAgeGreaterThan(age);
    }

    @Transactional
    public void deleteInactiveCustomers() {
        log.warn("Deleting all inactive customers");
        customerRepository.deleteByIsActiveFalse();
    }

    @Transactional
    public List<Customer> deleteOldCustomers(Integer age) {
        log.warn("Deleting customers older than {}", age);
        List<Customer> deleted = customerRepository.removeByAgeGreaterThan(age);
        deleted.forEach(this::calculateFullName);
        return deleted;
    }

    @Transactional
    public void deactivateCustomer(String id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setIsActive(false);
            customerRepository.save(customer);
            log.info("Deactivated customer: {} {}", customer.getName(), customer.getLastName());
        }
    }

    @Transactional
    public void activateCustomer(String id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setIsActive(true);
            customerRepository.save(customer);
            log.info("Activated customer: {} {}", customer.getName(), customer.getLastName());
        }
    }

    public List<Customer> searchFullName(String searchTerm) {
        List<Customer> customers = customerRepository.searchFullName(searchTerm);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findActiveCustomersInAgeRangeCustom(Integer minAge, Integer maxAge) {
        List<Customer> customers = customerRepository.findActiveCustomersInAgeRange(minAge, maxAge);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> findByNameOrLastNameFuzzy(String searchTerm) {
        List<Customer> customers = customerRepository.findByNameOrLastNameFuzzy(searchTerm);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    public List<Customer> advancedNameSearch(String searchTerm) {
        List<Customer> customers = customerRepository.advancedNameSearch(searchTerm);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    // ===== PAGINATION & SORTING =====

    // ✅ Artık doğru - repository'de Pageable version var
    public Page<Customer> findActiveCustomersPaged(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findByIsActive(true, pageable);
        customerPage.getContent().forEach(this::calculateFullName);
        return customerPage;
    }

    public List<Customer> findByNameContainingSorted(String name, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        List<Customer> customers = customerRepository.findByNameContaining(name, sort);
        customers.forEach(this::calculateFullName);
        return customers;
    }

    // ===== BUSINESS METHODS =====

    @Transactional
    public Customer updateCustomer(String id, CustomerSaveRequest request) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setName(request.getName());
            customer.setLastName(request.getLastName());
            customer.setAge(request.getAge());
            customer.setFullName(request.getName() + " " + request.getLastName());

            Customer updated = customerRepository.save(customer);
            log.info("Updated customer: {} {}", updated.getName(), updated.getLastName());
            return updated;
        }
        throw new RuntimeException("Customer not found with id: " + id);
    }

    public List<Customer> getCustomerStatistics() {
        log.info("=== Customer Statistics ===");
        log.info("Total customers: {}", customerRepository.count());
        log.info("Active customers: {}", countActiveCustomers());
        log.info("Inactive customers: {}", countInactiveCustomers());

        return findActiveCustomers();
    }

    public List<Customer> searchCustomers(String searchTerm) {
        log.info("Searching customers with term: {}", searchTerm);

        List<Customer> exactMatches = findByName(searchTerm);
        if (!exactMatches.isEmpty()) {
            return exactMatches;
        }

        List<Customer> fuzzyMatches = findByNameOrLastNameFuzzy(searchTerm);
        if (!fuzzyMatches.isEmpty()) {
            return fuzzyMatches;
        }

        return advancedNameSearch(searchTerm);
    }
}
