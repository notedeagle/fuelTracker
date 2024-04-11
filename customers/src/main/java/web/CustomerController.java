package web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import persistance.dto.CustomerUpdateDto;


@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping("/customer/{id}")
    ResponseEntity<Object> updateCustomer(@PathVariable long id, @RequestBody @Valid CustomerUpdateDto customer) {
        return customerService.editCustomer(id, customer);
    }
}
