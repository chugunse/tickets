package stm.route.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.carrier.mapper.CarrierMapper;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RouteFullDto;
import stm.route.model.Route;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarrierMapper.class, PointMapper.class})
public interface RouteMapper {
    @Mapping(target = "duration", dateFormat = "HH:mm")
    @Mapping(target = "departurePoint", ignore = true)
    @Mapping(target = "destinationPoint", ignore = true)
    Route toRouteModel(RouteNewDto dto);
    @Mapping(target = "duration", dateFormat = "HH:mm")
    RouteFullDto toRouteFullDto(Route model);

    List<RouteFullDto> toRouteFullDtoList(List<Route> list);

    Route toRouteModel(RouteFullDto dto);
}
