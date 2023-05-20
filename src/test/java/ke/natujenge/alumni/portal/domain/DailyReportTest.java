package ke.natujenge.alumni.portal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ke.natujenge.alumni.portal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyReport.class);
        DailyReport dailyReport1 = new DailyReport();
        dailyReport1.setId(1L);
        DailyReport dailyReport2 = new DailyReport();
        dailyReport2.setId(dailyReport1.getId());
        assertThat(dailyReport1).isEqualTo(dailyReport2);
        dailyReport2.setId(2L);
        assertThat(dailyReport1).isNotEqualTo(dailyReport2);
        dailyReport1.setId(null);
        assertThat(dailyReport1).isNotEqualTo(dailyReport2);
    }
}
