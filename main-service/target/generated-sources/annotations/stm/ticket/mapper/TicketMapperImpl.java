package stm.ticket.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import stm.ticket.dto.TicketDto;
import stm.ticket.model.Ticket;
import stm.trip.dto.TripFullDto;
import stm.trip.model.Trip;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-20T09:48:39+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public TicketDto toTicketDto(Ticket model) {
        if ( model == null ) {
            return null;
        }

        TicketDto ticketDto = new TicketDto();

        ticketDto.setId( model.getId() );
        ticketDto.setTrip( tripToTripFullDto( model.getTrip() ) );
        ticketDto.setPlaceNumber( model.getPlaceNumber() );

        return ticketDto;
    }

    @Override
    public List<TicketDto> toTicketDtoList(List<Ticket> list) {
        if ( list == null ) {
            return null;
        }

        List<TicketDto> list1 = new ArrayList<TicketDto>( list.size() );
        for ( Ticket ticket : list ) {
            list1.add( toTicketDto( ticket ) );
        }

        return list1;
    }

    protected TripFullDto tripToTripFullDto(Trip trip) {
        if ( trip == null ) {
            return null;
        }

        TripFullDto tripFullDto = new TripFullDto();

        tripFullDto.setId( trip.getId() );
        tripFullDto.setTitle( trip.getTitle() );
        tripFullDto.setRoute( trip.getRoute() );
        if ( trip.getDateTime() != null ) {
            tripFullDto.setDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( trip.getDateTime() ) );
        }
        tripFullDto.setPrice( trip.getPrice() );
        tripFullDto.setAmount( trip.getAmount() );

        return tripFullDto;
    }
}
