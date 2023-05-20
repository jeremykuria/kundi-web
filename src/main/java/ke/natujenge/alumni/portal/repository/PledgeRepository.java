package ke.natujenge.alumni.portal.repository;

import ke.natujenge.alumni.portal.domain.Pledge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pledge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {}
