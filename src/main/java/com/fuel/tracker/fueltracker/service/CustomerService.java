package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Customer;
import com.fuel.tracker.fueltracker.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User with user name %s not found";
    private final CustomerRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void singUpUser(Customer user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
}
