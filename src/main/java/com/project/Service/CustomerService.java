package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Customers;
import com.project.Repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customers> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customers getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customers createCustomer(Customers customer) {
        return customerRepository.save(customer);
    }

    public Customers updateCustomer(Long id, Customers customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id);
            return customerRepository.save(customer);
        }
        return null;
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for customer management
}
