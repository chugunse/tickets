package solds.model;

import dto.TicketSaveDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketSaveMapper {
    TicketSave toModel(TicketSaveDto dto);
}
