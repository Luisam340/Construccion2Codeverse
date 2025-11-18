package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, Long> {
    Optional<SpecialtyEntity> findByNameAndStatusTrue(String name);
    List<SpecialtyEntity> findByStatusTrue();
    boolean existsByName(String name);
}
