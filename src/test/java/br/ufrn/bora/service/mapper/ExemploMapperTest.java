package br.ufrn.bora.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExemploMapperTest {

    private ExemploMapper exemploMapper;

    @BeforeEach
    public void setUp() {
        exemploMapper = new ExemploMapperImpl();
    }
}
