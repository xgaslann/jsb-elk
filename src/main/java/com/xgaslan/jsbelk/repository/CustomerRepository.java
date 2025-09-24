package com.xgaslan.jsbelk.repository;

import com.xgaslan.jsbelk.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
    List<Customer> findByName(String name);
    List<Customer> findAllByNameLike(String name);
    List<Customer> findByNameStartingWith(String prefix);
    List<Customer> findByNameEndingWith(String suffix);
    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByLastName(String lastName);
    List<Customer> findByLastNameContaining(String lastName);
    List<Customer> findByLastNameIgnoreCase(String lastName);

    List<Customer> findByNameContaining(String name);
    List<Customer> findByNameContaining(String name, Sort sort);
    Page<Customer> findByNameContaining(String name, Pageable pageable);

    List<Customer> findByNameAndLastName(String name, String lastName);
    List<Customer> findByNameOrLastName(String name, String lastName);
    List<Customer> findByNameContainingAndLastNameContaining(String name, String lastName);

    List<Customer> findByAge(Integer age);
    List<Customer> findByAgeGreaterThan(Integer age);
    List<Customer> findByAgeLessThan(Integer age);
    List<Customer> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Customer> findByAgeGreaterThanEqual(Integer age);
    List<Customer> findByAgeLessThanEqual(Integer age);
    List<Customer> findByAgeIn(List<Integer> ages);
    List<Customer> findByAgeNotNull();
    List<Customer> findByAgeIsNull();

    List<Customer> findByIsActive(Boolean isActive);
    Page<Customer> findByIsActive(Boolean isActive, Pageable pageable);
    List<Customer> findByIsActiveTrue();
    List<Customer> findByIsActiveFalse();
    List<Customer> findByIsActiveIsNull();

    List<Customer> findByNameContainingAndAgeGreaterThan(String name, Integer age);
    List<Customer> findByIsActiveTrueAndAgeBetween(Integer minAge, Integer maxAge);
    List<Customer> findByNameOrLastNameAndIsActiveTrue(String name, String lastName);
    List<Customer> findByAgeGreaterThanAndIsActiveTrueOrderByNameAsc(Integer age);

    long countByIsActive(Boolean isActive);
    long countByAgeBetween(Integer minAge, Integer maxAge);
    boolean existsByNameAndLastName(String name, String lastName);
    boolean existsByIsActiveTrueAndAgeGreaterThan(Integer age);

    void deleteByIsActiveFalse();
    List<Customer> removeByAgeGreaterThan(Integer age);

    // Full name search (name + lastName combination)
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"lastName\": \"?0\"}}], \"minimum_should_match\": 1}}")
    List<Customer> searchFullName(String searchTerm);

    // Active customers within age range
    @Query("{\"bool\": {\"must\": [{\"term\": {\"isActive\": true}}, {\"range\": {\"age\": {\"gte\": ?0, \"lte\": ?1}}}]}}")
    List<Customer> findActiveCustomersInAgeRange(Integer minAge, Integer maxAge);

    // Fuzzy search on both name fields
    @Query("{\"bool\": {\"should\": [{\"fuzzy\": {\"name\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}, {\"fuzzy\": {\"lastName\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}]}}")
    List<Customer> findByNameOrLastNameFuzzy(String searchTerm);

    // Advanced multi-field search
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name^2\", \"lastName^2\"], \"type\": \"best_fields\", \"fuzziness\": \"AUTO\"}}")
    List<Customer> advancedNameSearch(String searchTerm);

    // Age statistics query
    @Query("{\"bool\": {\"must\": [{\"term\": {\"isActive\": true}}]}}")
    List<Customer> findActiveCustomersForAgeStats();
}
