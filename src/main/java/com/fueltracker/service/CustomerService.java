package com.fueltracker.service;

import com.fueltracker.model.dto.CustomerUpdateDto;
import com.fueltracker.model.entity.Customer;
import com.fueltracker.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User with user name %s not found";
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void singUpUser(Customer user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        customerRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    @Transactional
    public ResponseEntity<Object> editCustomer(long id, CustomerUpdateDto customer) {
        boolean userUsernameExist = customerRepository.findByUsername(customer.getUsername()).isPresent();
        boolean userEmailExist = customerRepository.findByEmail(customer.getEmail()).isPresent();

        if (userUsernameExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        } else if (userEmailExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address already taken.");
        }

        Customer customerEdited = customerRepository.findById(id).orElseThrow();
        customerEdited.updateFrom(customer);

        return ResponseEntity.noContent().build();
    }
}
