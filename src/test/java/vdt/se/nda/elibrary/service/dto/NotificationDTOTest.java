package vdt.se.nda.elibrary.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vdt.se.nda.elibrary.web.rest.TestUtil;

class NotificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationDTO.class);
        NotificationDTO notificationDTO1 = new NotificationDTO();
        notificationDTO1.setId(1L);
        NotificationDTO notificationDTO2 = new NotificationDTO();
        assertThat(notificationDTO1).isNotEqualTo(notificationDTO2);
        notificationDTO2.setId(notificationDTO1.getId());
        assertThat(notificationDTO1).isEqualTo(notificationDTO2);
        notificationDTO2.setId(2L);
        assertThat(notificationDTO1).isNotEqualTo(notificationDTO2);
        notificationDTO1.setId(null);
        assertThat(notificationDTO1).isNotEqualTo(notificationDTO2);
    }
}
