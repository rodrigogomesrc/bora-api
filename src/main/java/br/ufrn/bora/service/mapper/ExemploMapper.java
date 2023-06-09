package br.ufrn.bora.service.mapper;

import br.ufrn.bora.domain.Exemplo;
import br.ufrn.bora.service.dto.ExemploDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exemplo} and its DTO {@link ExemploDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExemploMapper extends EntityMapper<ExemploDTO, Exemplo> {}
