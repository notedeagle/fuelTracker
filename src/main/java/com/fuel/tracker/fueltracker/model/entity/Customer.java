package com.fuel.tracker.fueltracker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseEntity implements UserDetails {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private boolean locked = false;
    private boolean enabled = true;
    @Enumerated(EnumType.STRING)
    private CustomerRole userRole = CustomerRole.USER;

    @OneToMany(mappedBy = "customer")
    private Set<Vehicle> vehicles;

    public Customer(String username, String email, String firstName, String lastname, String password) {
        this.username = username;
        this.email = email;
        this.firstname = firstName;
        this.lastname = lastname;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
