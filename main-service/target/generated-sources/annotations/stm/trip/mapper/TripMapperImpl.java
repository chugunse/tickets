package stm.trip.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import stm.carrier.dto.CarrierDto;
import stm.carrier.model.Carrier;
import stm.route.dto.PointDto;
import stm.route.dto.RoutePublicDto;
import stm.route.model.Point;
import stm.route.model.Route;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripPublicDto;
import stm.trip.model.Trip;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-12T14:37:41+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
@Component
public class TripMapperImpl implements TripMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_01172057030 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );

    @Override
    public Trip toModel(TripNewDto dto) {
        if ( dto == null ) {
            return null;
        }

        Trip trip = new Trip();

        if ( dto.getDateTime() != null ) {
            trip.setDateTime( LocalDateTime.parse( dto.getDateTime(), dateTimeFormatter_yyyy_MM_dd_HH_mm_01172057030 ) );
        }
        trip.setTitle( dto.getTitle() );
        trip.setPrice( dto.getPrice() );
        trip.setAmount( dto.getAmount() );

        return trip;
    }

    @Override
    public TripFullDto toFullDto(Trip model) {
        if ( model == null ) {
            return null;
        }

        TripFullDto tripFullDto = new TripFullDto();

        if ( model.getDateTime() != null ) {
            tripFullDto.setDateTime( dateTimeFormatter_yyyy_MM_dd_HH_mm_01172057030.format( model.getDateTime() ) );
        }
        tripFullDto.setId( model.getId() );
        tripFullDto.setTitle( model.getTitle() );
        tripFullDto.setRoute( model.getRoute() );
        tripFullDto.setPrice( model.getPrice() );
        tripFullDto.setAmount( model.getAmount() );

        return tripFullDto;
    }

    @Override
    public List<TripFullDto> toFullDtoList(List<Trip> list) {
        if ( list == null ) {
            return null;
        }

        List<TripFullDto> list1 = new ArrayList<TripFullDto>( list.size() );
        for ( Trip trip : list ) {
            list1.add( toFullDto( trip ) );
        }

        return list1;
    }

    @Override
    public TripPublicDto toPublicDto(Trip trip) {
        if ( trip == null ) {
            return null;
        }

        TripPublicDto tripPublicDto = new TripPublicDto();

        tripPublicDto.setTitle( trip.getTitle() );
        tripPublicDto.setRoute( routeToRoutePublicDto( trip.getRoute() ) );
        if ( trip.getDateTime() != null ) {
            tripPublicDto.setDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( trip.getDateTime() ) );
        }
        tripPublicDto.setPrice( trip.getPrice() );

        return tripPublicDto;
    }

    protected PointDto pointToPointDto(Point point) {
        if ( point == null ) {
            return null;
        }

        PointDto pointDto = new PointDto();

        pointDto.setId( point.getId() );
        pointDto.setTitle( point.getTitle() );

        return pointDto;
    }

    protected CarrierDto carrierToCarrierDto(Carrier carrier) {
        if ( carrier == null ) {
            return null;
        }

        CarrierDto carrierDto = new CarrierDto();

        carrierDto.setCompany( carrier.getCompany() );
        carrierDto.setPhone( carrier.getPhone() );

        return carrierDto;
    }

    protected RoutePublicDto routeToRoutePublicDto(Route route) {
        if ( route == null ) {
            return null;
        }

        RoutePublicDto routePublicDto = new RoutePublicDto();

        routePublicDto.setRouteNumber( route.getRouteNumber() );
        routePublicDto.setDeparturePoint( pointToPointDto( route.getDeparturePoint() ) );
        routePublicDto.setDestinationPoint( pointToPointDto( route.getDestinationPoint() ) );
        routePublicDto.setCarrier( carrierToCarrierDto( route.getCarrier() ) );
        if ( route.getDuration() != null ) {
            routePublicDto.setDuration( DateTimeFormatter.ISO_LOCAL_TIME.format( route.getDuration() ) );
        }

        return routePublicDto;
    }
}
