package com.fueltracker.config;

import com.fueltracker.model.dto.RefuelDto;
import com.fueltracker.model.dto.VehicleDto;
import com.fueltracker.model.entity.Refuel;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.repository.CustomerRepository;
import com.fueltracker.utils.CostCalculator;
import com.fueltracker.utils.DateCalculator;
import com.fueltracker.utils.DistanceCalculator;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
public class Config {

    private final CustomerRepository customerRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        modelMapper.createTypeMap(Vehicle.class, VehicleDto.class)
                .addMappings(mapper -> mapper.map(Vehicle::getType, VehicleDto::setVehicleType));

        modelMapper.createTypeMap(VehicleDto.class, Vehicle.class)
                .addMappings(mapper -> mapper.map(VehicleDto::getVehicleType, Vehicle::setType));

        modelMapper.createTypeMap(Refuel.class, RefuelDto.class);
        modelMapper.createTypeMap(RefuelDto.class, Refuel.class);

        return modelMapper;
    }

    @Bean
    public CostCalculator costCalculator() {
        return new CostCalculator();
    }

    @Bean
    public DistanceCalculator distanceCalculator() {
        return new DistanceCalculator();
    }

    @Bean
    public DateCalculator dateCalculator() {
        return new DateCalculator();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
