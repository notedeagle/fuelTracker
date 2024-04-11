package web;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import persistance.dto.CustomerUpdateDto;
import persistance.entity.Customer;
import persistance.repository.CustomerRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@ComponentScan(basePackageClasses = CustomerRepository.class)
@EnableJpaRepositories(basePackageClasses = CustomerRepository.class)
@EntityScan(basePackageClasses = Customer.class)
public class CustomerService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User with user name %s not found";

    private final CustomerRepository customerRepository;

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

    public Optional<Customer> findByUsername(@Param("userName") String userName) {
        return customerRepository.findByUsername(userName);
    }
}
