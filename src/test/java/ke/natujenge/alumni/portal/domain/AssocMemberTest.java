package ke.natujenge.alumni.portal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ke.natujenge.alumni.portal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssocMemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssocMember.class);
        AssocMember assocMember1 = new AssocMember();
        assocMember1.setId(1L);
        AssocMember assocMember2 = new AssocMember();
        assocMember2.setId(assocMember1.getId());
        assertThat(assocMember1).isEqualTo(assocMember2);
        assocMember2.setId(2L);
        assertThat(assocMember1).isNotEqualTo(assocMember2);
        assocMember1.setId(null);
        assertThat(assocMember1).isNotEqualTo(assocMember2);
    }
}
