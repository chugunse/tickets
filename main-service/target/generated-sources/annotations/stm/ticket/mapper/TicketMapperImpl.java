package stm.ticket.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import stm.carrier.dto.CarrierDto;
import stm.carrier.model.Carrier;
import stm.route.dto.PointDto;
import stm.route.dto.RoutePublicDto;
import stm.route.model.Point;
import stm.route.model.Route;
import stm.ticket.dto.TicketDto;
import stm.ticket.dto.TicketNewDto;
import stm.ticket.model.Ticket;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-12T14:37:41+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_01172057030 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );

    @Override
    public Ticket toModel(TicketNewDto dto) {
        if ( dto == null ) {
            return null;
        }

        LocalDateTime dateTime = null;
        String trip = null;
        int price = 0;

        if ( dto.getDateTime() != null ) {
            dateTime = LocalDateTime.parse( dto.getDateTime(), dateTimeFormatter_yyyy_MM_dd_HH_mm_01172057030 );
        }
        trip = dto.getTrip();
        price = dto.getPrice();

        Route route = null;
        int id = 0;
        int placeNumber = 0;

        Ticket ticket = new Ticket( id, trip, route, dateTime, placeNumber, price );

        return ticket;
    }

    @Override
    public TicketDto toTicketDto(Ticket model) {
        if ( model == null ) {
            return null;
        }

        TicketDto ticketDto = new TicketDto();

        ticketDto.setId( model.getId() );
        ticketDto.setTrip( model.getTrip() );
        ticketDto.setRoute( routeToRoutePublicDto( model.getRoute() ) );
        ticketDto.setDateTime( model.getDateTime() );
        ticketDto.setPlaceNumber( model.getPlaceNumber() );
        ticketDto.setPrice( model.getPrice() );

        return ticketDto;
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
