package fi.lipp.greatheart.directory._old.repository_old;

import fi.lipp.greatheart.directory._old.domain_old.BenefactorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenefactorRepository extends JpaRepository<BenefactorEntity, Long> {
    Page<BenefactorEntity> findAll(Pageable pageable);
}
