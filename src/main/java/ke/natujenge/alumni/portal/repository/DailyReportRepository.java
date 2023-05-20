package ke.natujenge.alumni.portal.repository;

import ke.natujenge.alumni.portal.domain.DailyReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DailyReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {}
