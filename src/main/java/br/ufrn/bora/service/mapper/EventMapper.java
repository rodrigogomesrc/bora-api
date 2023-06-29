package br.ufrn.bora.service.mapper;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.domain.Location;
import br.ufrn.bora.service.dto.EventDTO;
import br.ufrn.bora.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    EventDTO toDto(Event s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
