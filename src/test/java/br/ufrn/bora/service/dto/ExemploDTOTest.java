package br.ufrn.bora.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufrn.bora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExemploDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExemploDTO.class);
        ExemploDTO exemploDTO1 = new ExemploDTO();
        exemploDTO1.setId("id1");
        ExemploDTO exemploDTO2 = new ExemploDTO();
        assertThat(exemploDTO1).isNotEqualTo(exemploDTO2);
        exemploDTO2.setId(exemploDTO1.getId());
        assertThat(exemploDTO1).isEqualTo(exemploDTO2);
        exemploDTO2.setId("id2");
        assertThat(exemploDTO1).isNotEqualTo(exemploDTO2);
        exemploDTO1.setId(null);
        assertThat(exemploDTO1).isNotEqualTo(exemploDTO2);
    }
}
