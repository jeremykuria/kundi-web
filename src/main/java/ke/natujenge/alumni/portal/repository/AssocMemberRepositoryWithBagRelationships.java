package ke.natujenge.alumni.portal.repository;

import java.util.List;
import java.util.Optional;
import ke.natujenge.alumni.portal.domain.AssocMember;
import org.springframework.data.domain.Page;

public interface AssocMemberRepositoryWithBagRelationships {
    Optional<AssocMember> fetchBagRelationships(Optional<AssocMember> assocMember);

    List<AssocMember> fetchBagRelationships(List<AssocMember> assocMembers);

    Page<AssocMember> fetchBagRelationships(Page<AssocMember> assocMembers);
}
