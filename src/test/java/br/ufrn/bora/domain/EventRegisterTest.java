package br.ufrn.bora.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufrn.bora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventRegisterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventRegister.class);
        EventRegister eventRegister1 = new EventRegister();
        eventRegister1.setId("id1");
        EventRegister eventRegister2 = new EventRegister();
        eventRegister2.setId(eventRegister1.getId());
        assertThat(eventRegister1).isEqualTo(eventRegister2);
        eventRegister2.setId("id2");
        assertThat(eventRegister1).isNotEqualTo(eventRegister2);
        eventRegister1.setId(null);
        assertThat(eventRegister1).isNotEqualTo(eventRegister2);
    }
}
