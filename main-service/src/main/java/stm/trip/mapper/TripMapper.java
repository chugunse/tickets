package stm.trip.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.route.mapper.RouteMapper;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripPublicDto;
import stm.trip.model.Trip;

import java.util.List;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface TripMapper {
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "dateTime", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "id", ignore = true)
    Trip toModel(TripNewDto dto);

    @Mapping(target = "dateTime", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "sold", ignore = true)
    TripFullDto toFullDto(Trip model);

    List<TripFullDto> toFullDtoList(List<Trip> list);

    @Mapping(target = "available", ignore = true)
    TripPublicDto toPublicDto(Trip trip);
}
