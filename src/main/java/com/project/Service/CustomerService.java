package com.project.Service;

import java.util.List;

import com.project.Entity.User;
import com.project.Repository.UserRepository;
import org.springframework.stereotype.Service;
import com.project.Entity.Customers;
import com.project.Repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public List<Customers> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customers getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
    
    public Customers findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customers createCustomer(Customers customer) {
        return customerRepository.save(customer);
    }

    public Customers updateCustomer(Long id, Customers customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id);
            User user = customer.getUser();
            Customers c = customerRepository.save(customer);
            userRepository.save(user);
            return c;
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
