package ke.natujenge.alumni.portal.repository;

import ke.natujenge.alumni.portal.domain.Association;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Association entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {}
