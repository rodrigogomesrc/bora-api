package br.ufrn.bora.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufrn.bora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketDTO.class);
        TicketDTO ticketDTO1 = new TicketDTO();
        ticketDTO1.setId("id1");
        TicketDTO ticketDTO2 = new TicketDTO();
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
        ticketDTO2.setId(ticketDTO1.getId());
        assertThat(ticketDTO1).isEqualTo(ticketDTO2);
        ticketDTO2.setId("id2");
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
        ticketDTO1.setId(null);
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
    }
}
