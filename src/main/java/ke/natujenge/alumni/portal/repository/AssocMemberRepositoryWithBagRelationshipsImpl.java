package ke.natujenge.alumni.portal.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ke.natujenge.alumni.portal.domain.AssocMember;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AssocMemberRepositoryWithBagRelationshipsImpl implements AssocMemberRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AssocMember> fetchBagRelationships(Optional<AssocMember> assocMember) {
        return assocMember.map(this::fetchAssociations);
    }

    @Override
    public Page<AssocMember> fetchBagRelationships(Page<AssocMember> assocMembers) {
        return new PageImpl<>(
            fetchBagRelationships(assocMembers.getContent()),
            assocMembers.getPageable(),
            assocMembers.getTotalElements()
        );
    }

    @Override
    public List<AssocMember> fetchBagRelationships(List<AssocMember> assocMembers) {
        return Optional.of(assocMembers).map(this::fetchAssociations).orElse(Collections.emptyList());
    }

    AssocMember fetchAssociations(AssocMember result) {
        return entityManager
            .createQuery(
                "select assocMember from AssocMember assocMember left join fetch assocMember.associations where assocMember is :assocMember",
                AssocMember.class
            )
            .setParameter("assocMember", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<AssocMember> fetchAssociations(List<AssocMember> assocMembers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, assocMembers.size()).forEach(index -> order.put(assocMembers.get(index).getId(), index));
        List<AssocMember> result = entityManager
            .createQuery(
                "select distinct assocMember from AssocMember assocMember left join fetch assocMember.associations where assocMember in :assocMembers",
                AssocMember.class
            )
            .setParameter("assocMembers", assocMembers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
