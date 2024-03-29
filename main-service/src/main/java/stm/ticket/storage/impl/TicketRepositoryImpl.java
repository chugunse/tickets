package stm.ticket.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stm.carrier.model.Carrier;
import stm.route.model.Point;
import stm.route.model.Route;
import stm.ticket.model.Ticket;
import stm.ticket.storage.TicketRepository;
import stm.trip.model.Trip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Ticket> getTickets(String rangeStart, String rangeEnd,
                                   Long departurePointId, Long destinationPointId,
                                   String departurePoint, String destinationPoint,
                                   String carrier, Integer from, Integer size) {
        StringBuilder sqlQuery = new StringBuilder("select ticket.id as ticketId, ticket.place_number as tickePlaceN, " +
                "trip.id as tripId, trip.title as tripTitle, trip.date_time as tripTime, trip.price as tripPrice, " +
                "trip.amount as tripAmount," +
                "route.id as routeId, route.route_number as routeNumber, route.duration as RouteDuration, " +
                "carrier.id as carrierId, carrier.company as carrierCompany, carrier.phone as carrierPhone, " +
                "fp.id as fpi, fp.title as fpt, " +
                "sp.id as spi, sp.title as spt " +
                "from ticket " +
                "join trip on trip.id = ticket.trip_id " +
                "join route on route.id = trip.route_id " +
                "join carrier on carrier.id =route.carrier_id " +
                "join point fp on fp.id = route.departure_point_id " +
                "join point sp on sp.id = route.destination_point_id " +
                "where ticket.user_id is null ");
        if (rangeStart != null) {
            sqlQuery.append("and trip.date_time > to_timestamp('")
                    .append(rangeStart)
                    .append("', 'yyyy-mm-dd hh24:mi') ");
        }
        if (rangeEnd != null) {
            sqlQuery.append("and trip.date_time < to_timestamp('")
                    .append(rangeEnd)
                    .append("', 'yyyy-mm-dd hh24:mi') ");
        }
        if (departurePointId != null) {
            sqlQuery.append("and route.departure_point_id = ")
                    .append(departurePointId)
                    .append(" ");
        }
        if (departurePoint != null) {
            sqlQuery.append("and  lower(fp.title) like lower('%")
                    .append(departurePoint)
                    .append("%') ");
        }
        if (destinationPointId != null) {
            sqlQuery.append("and route.destination_point_id = ")
                    .append(destinationPointId)
                    .append(" ");
        }
        if (destinationPoint != null) {
            sqlQuery.append("and  lower(sp.title) like lower('%")
                    .append(destinationPoint)
                    .append("%') ");
        }
        if (carrier != null) {
            sqlQuery.append("and  lower(carrier.company) like lower('%")
                    .append(carrier)
                    .append("%') ");
        }
        sqlQuery.append("order by trip.date_time, ticket.id asc LIMIT ")
                .append(size)
                .append(" OFFSET ")
                .append(from);
        return jdbcTemplate.query(sqlQuery.toString(), this::makeTicket);
    }

    private Ticket makeTicket(ResultSet resultSet, int rowNum) throws SQLException {
        Carrier carrier = Carrier.builder()
                .id(resultSet.getLong("carrierId"))
                .company(resultSet.getString("carrierCompany"))
                .phone(resultSet.getString("carrierPhone"))
                .build();
        Route route = Route.builder()
                .id(resultSet.getLong("routeId"))
                .routeNumber(resultSet.getString("routeNumber"))
                .departurePoint(new Point(resultSet.getLong("fpi"), resultSet.getString("fpt")))
                .destinationPoint(new Point(resultSet.getLong("spi"), resultSet.getString("spt")))
                .duration(resultSet.getTime("RouteDuration").toLocalTime())
                .carrier(carrier)
                .build();
        Trip trip = Trip.builder()
                .id(resultSet.getLong("tripId"))
                .title(resultSet.getString("tripTitle"))
                .dateTime(resultSet.getObject("tripTime", LocalDateTime.class))
                .price(resultSet.getInt("tripPrice"))
                .amount(resultSet.getInt("tripAmount"))
                .route(route)
                .build();
        return Ticket.builder()
                .id(resultSet.getLong("ticketId"))
                .placeNumber(resultSet.getInt("tickePlaceN"))
                .trip(trip)
                .build();
    }

    @Override
    public Ticket getTicket(Long id) {
        String sqlQuery = "select ticket.id as ticketId, ticket.place_number as tickePlaceN, " +
                "trip.id as tripId, trip.title as tripTitle, trip.date_time as tripTime, trip.price as tripPrice, " +
                "trip.amount as tripAmount," +
                "route.id as routeId, route.route_number as routeNumber, route.duration as RouteDuration, " +
                "carrier.id as carrierId, carrier.company as carrierCompany, carrier.phone as carrierPhone, " +
                "fp.id as fpi, fp.title as fpt, " +
                "sp.id as spi, sp.title as spt " +
                "from ticket " +
                "join trip on trip.id = ticket.trip_id " +
                "join route on route.id = trip.route_id " +
                "join carrier on carrier.id =route.carrier_id " +
                "join point fp on fp.id = route.departure_point_id " +
                "join point sp on sp.id = route.destination_point_id " +
                "where ticket.id = ? and ticket.user_id is null";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeTicket, id);
    }

    @Override
    public void ticketSetUser(Long ticketId, Long userId) {
        final String sqlQuery = "update ticket set user_id = ? where id = ?";
        jdbcTemplate.update(sqlQuery, userId, ticketId);
    }

    @Override
    public List<Ticket> getUserTickets(Long id) {
        String sqlQuery = "select ticket.id as ticketId, ticket.place_number as tickePlaceN, " +
                "trip.id as tripId, trip.title as tripTitle, trip.date_time as tripTime, trip.price as tripPrice, " +
                "trip.amount as tripAmount," +
                "route.id as routeId, route.route_number as routeNumber, route.duration as RouteDuration, " +
                "carrier.id as carrierId, carrier.company as carrierCompany, carrier.phone as carrierPhone, " +
                "fp.id as fpi, fp.title as fpt, " +
                "sp.id as spi, sp.title as spt " +
                "from ticket " +
                "join trip on trip.id = ticket.trip_id " +
                "join route on route.id = trip.route_id " +
                "join carrier on carrier.id =route.carrier_id " +
                "join point fp on fp.id = route.departure_point_id " +
                "join point sp on sp.id = route.destination_point_id " +
                "where ticket.user_id = ?";
        return jdbcTemplate.query(sqlQuery, this::makeTicket, id);
    }
}
