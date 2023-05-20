package ke.natujenge.alumni.portal.repository;

import java.util.List;
import java.util.Optional;
import ke.natujenge.alumni.portal.domain.AssocMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssocMember entity.
 *
 * When extending this class, extend AssocMemberRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AssocMemberRepository extends AssocMemberRepositoryWithBagRelationships, JpaRepository<AssocMember, Long> {
    default Optional<AssocMember> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<AssocMember> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<AssocMember> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
