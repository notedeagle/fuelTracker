package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Customer;
import com.fuel.tracker.fueltracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User with user name %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void singUpUser(Customer user) {
        boolean userUsernameExist = userRepository.findByUsername(user.getUsername()).isPresent();
        boolean userEmailExist = userRepository.findByEmail(user.getEmail()).isPresent();

        //TODO: Custom exceptions
        if (userUsernameExist) {
            throw new IllegalStateException("Email taken");
        } else if (userEmailExist) {
            throw new IllegalStateException("Username taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
