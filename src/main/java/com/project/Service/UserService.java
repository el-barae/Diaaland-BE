package com.project.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.project.Entity.*;
import com.project.Repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.Repository.CandidateRepository;
import com.project.Repository.CustomerRepository;
import com.project.Repository.UserRepository;
import com.project.model.ChangePasswordRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Service
public class UserService {
	private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final CustomerRepository customerRepository;

    public UserService(UserRepository userRepository, CandidateRepository candidateRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.customerRepository = customerRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public Long getRelatedIdByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (user.getRole() == Role.CANDIDAT) {
            Candidates candidate = candidateRepository.findByUser(user);
            return (candidate != null) ? candidate.getId() : null;
        } else if (user.getRole() == Role.CUSTOMER) {
            Customers customer = customerRepository.findByUser(user);
            return (customer != null) ? customer.getId() : null;
        }else if (user.getRole() == Role.ADMIN) {
            return null;
        }

        return null;
    }


    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }
}