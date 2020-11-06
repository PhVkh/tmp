package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.WardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardsRepository extends JpaRepository<WardEntity, Long> {
    Page<WardEntity> findAll(Pageable pageable);
}
