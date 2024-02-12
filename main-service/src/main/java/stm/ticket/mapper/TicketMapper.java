package stm.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.route.mapper.RouteMapper;
import stm.ticket.dto.TicketDto;
import stm.ticket.dto.TicketNewDto;
import stm.ticket.model.Ticket;

import java.util.List;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface TicketMapper {
    TicketDto toTicketDto(Ticket model);
    List<TicketDto> toTicketDtoList(List<Ticket> list);
}
