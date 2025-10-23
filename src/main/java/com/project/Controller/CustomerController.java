
package com.project.Controller;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;

import com.project.Service.FileStorageService;
import com.project.model.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Entity.Customers;
import com.project.Service.CustomerService;

@RestController
@RequestMapping("/api/v1/users/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final FileStorageService fileStorageService;

    public CustomerController(CustomerService customerService, FileStorageService fileStorageService) {
        this.customerService = customerService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<Customers> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable Long id) {
        Customers customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/findIdByEmail/{email}")
    public Long findIdByEmail(@PathVariable String email) {
        Customers customer = customerService.findByEmail(email);

        if (customer != null) {
            return customer.getId();
        } else {
            return null;
        }
    }
    
    @GetMapping("/tostring/{id}")
    public ResponseEntity<String> getCustomerByIdToString(@PathVariable Long id) {
        String s = customerService.getCustomerById(id).toString();
        if (s != null) {
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/name/{id}")
    public ResponseEntity<String> getNameById(@PathVariable Long id) {
        String name = customerService.getCustomerById(id).getName();
        if (name != null) {
            return ResponseEntity.ok(name);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/logo/{id}")
    public ResponseEntity<Resource> getLogoFile(@PathVariable Long id) {
        try {
            Customers customer = customerService.getCustomerById(id);
            if (customer == null || customer.getLogo() == null || customer.getLogo().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // customer.getLogo() doit contenir juste le nom du fichier (ex: "logo123.png")
            Resource resource = fileStorageService.loadFileAsResource(customer.getLogo());
            if (resource == null) {
                return ResponseEntity.notFound().build();
            }

            // Détection dynamique du type MIME
            String contentType = Files.probeContentType(resource.getFile().toPath());
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping
    public ResponseEntity<Customers> createCustomer(@RequestBody Customers customer) {
        Customers newCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customers> updateCustomer(@PathVariable Long id, @RequestBody Customers customer) {
        Customers updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomer(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for customer management
}
