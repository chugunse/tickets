package stm.route.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stm.carrier.mapper.CarrierMapper;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RouteNewDto;
import stm.route.model.Route;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T16:33:12+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
@Component
public class RouteMapperImpl implements RouteMapper {

    @Autowired
    private CarrierMapper carrierMapper;
    @Autowired
    private PointMapper pointMapper;
    private final DateTimeFormatter dateTimeFormatter_HH_mm_168697690 = DateTimeFormatter.ofPattern( "HH:mm" );

    @Override
    public Route toRouteModel(RouteNewDto dto) {
        if ( dto == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        if ( dto.getDuration() != null ) {
            route.duration( LocalTime.parse( dto.getDuration(), dateTimeFormatter_HH_mm_168697690 ) );
        }
        route.routeNumber( dto.getRouteNumber() );

        return route.build();
    }

    @Override
    public RouteFullDto toRouteFullDto(Route model) {
        if ( model == null ) {
            return null;
        }

        RouteFullDto routeFullDto = new RouteFullDto();

        if ( model.getDuration() != null ) {
            routeFullDto.setDuration( dateTimeFormatter_HH_mm_168697690.format( model.getDuration() ) );
        }
        routeFullDto.setId( model.getId() );
        routeFullDto.setRouteNumber( model.getRouteNumber() );
        routeFullDto.setDeparturePoint( pointMapper.toDto( model.getDeparturePoint() ) );
        routeFullDto.setDestinationPoint( pointMapper.toDto( model.getDestinationPoint() ) );
        routeFullDto.setCarrier( carrierMapper.toCarrierFullDto( model.getCarrier() ) );

        return routeFullDto;
    }

    @Override
    public List<RouteFullDto> toRouteFullDtoList(List<Route> list) {
        if ( list == null ) {
            return null;
        }

        List<RouteFullDto> list1 = new ArrayList<RouteFullDto>( list.size() );
        for ( Route route : list ) {
            list1.add( toRouteFullDto( route ) );
        }

        return list1;
    }

    @Override
    public Route toRouteModel(RouteFullDto dto) {
        if ( dto == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        route.id( dto.getId() );
        route.routeNumber( dto.getRouteNumber() );
        route.departurePoint( pointMapper.toModel( dto.getDeparturePoint() ) );
        route.destinationPoint( pointMapper.toModel( dto.getDestinationPoint() ) );
        route.carrier( carrierMapper.toCarrierModel( dto.getCarrier() ) );
        if ( dto.getDuration() != null ) {
            route.duration( LocalTime.parse( dto.getDuration() ) );
        }

        return route.build();
    }
}
