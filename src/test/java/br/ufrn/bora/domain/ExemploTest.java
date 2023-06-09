package br.ufrn.bora.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufrn.bora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExemploTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exemplo.class);
        Exemplo exemplo1 = new Exemplo();
        exemplo1.setId("id1");
        Exemplo exemplo2 = new Exemplo();
        exemplo2.setId(exemplo1.getId());
        assertThat(exemplo1).isEqualTo(exemplo2);
        exemplo2.setId("id2");
        assertThat(exemplo1).isNotEqualTo(exemplo2);
        exemplo1.setId(null);
        assertThat(exemplo1).isNotEqualTo(exemplo2);
    }
}
