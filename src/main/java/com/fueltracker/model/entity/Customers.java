package com.fueltracker.model.entity;

import com.fueltracker.model.dto.CustomerUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
public class Customers implements UserDetails {

    @Id
    @NotNull
    @Column(name = "CUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "CUS_CREATED")
    private LocalDateTime created;

    @NotNull
    @LastModifiedDate
    @Column(name = "CUS_UPDATED")
    private LocalDateTime updated;

    @NotBlank
    @Column(name = "CUS_USERNAME")
    private String username;

    @Email
    @NotBlank
    @Column(name = "CUS_EMAIL")
    private String email;

    @NotBlank
    @Column(name = "CUS_FIRST_NAME")
    private String firstname;

    @NotBlank
    @Column(name = "CUS_LAST_NAME")
    private String lastname;

    @NotBlank
    @Column(name = "CUS_PASSWORD")
    private String password;

    @NotNull
    @Column(name = "CUS_LOCKED")
    private Boolean locked;

    @NotNull
    @Column(name = "CUS_ENABLED")
    private Boolean enabled;

    @NotNull
    @Column(name = "CUS_USER_ROLE")
    @Enumerated(EnumType.STRING)
    private CustomerRole userRole = CustomerRole.USER;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vehicles> vehicles;

    public Customers(String username, String email, String firstName, String lastname, String password) {
        this.username = username;
        this.email = email;
        this.firstname = firstName;
        this.lastname = lastname;
        this.password = password;
    }


    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
        locked = false;
        enabled = true;
    }

    @PreUpdate
    void preUpdate() {
        updated = LocalDateTime.now();
    }

    public void updateFrom(CustomerUpdateDto source) {
        username = source.getUsername();
        email = source.getEmail();
        firstname = source.getFirstName();
        lastname = source.getLastName();
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
        return username;
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
