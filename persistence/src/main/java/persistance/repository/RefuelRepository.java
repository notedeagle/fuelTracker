package persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persistance.entity.Refuel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefuelRepository extends JpaRepository<Refuel, Long> {
    Optional<List<Refuel>> findAllByVehicleId(@Param("id") Long id);
}
