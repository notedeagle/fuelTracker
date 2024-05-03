package persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import persistance.dto.CustomerUpdateDto;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer implements UserDetails {

    @Serial
    @Transient
    private static final long serialVersionUID = 8132281207583508963L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private boolean locked;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private CustomerRole userRole = CustomerRole.USER;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private Set<Vehicle> vehicles;

    public Customer(String username, String email, String firstName, String lastname, String password) {
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

    public void updateFrom(final CustomerUpdateDto source) {
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
