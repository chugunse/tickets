package stm.route.storage.imlp;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import stm.carrier.model.Carrier;
import stm.route.model.Point;
import stm.route.model.Route;
import stm.route.storage.RouteRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(Route route) {
        final String sqlQuery = "INSERT INTO route (route_number, departure_point_id, " +
                "destination_point_id, carrier_id, duration) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, route.getRouteNumber());
            stmt.setInt(2, route.getDeparturePoint().getId());
            stmt.setInt(3, route.getDestinationPoint().getId());
            stmt.setInt(4, route.getCarrier().getId());
            stmt.setObject(5, route.getDuration());
            return stmt;
        }, generatedId);
        return Objects.requireNonNull(generatedId.getKey()).intValue();
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM route WHERE id = ?", id);
    }

    @Override
    public Route getById(int id) {
        final String sqlQuery = "SELECT route.id, route.route_number, " +
                "fp.id fpi , fp.title fpt, " +
                "sp.id spi , sp.title spt, " +
                "route.duration, " +
                "c.id carId, c.company, c.phone " +
                "FROM route " +
                "join carrier c on c.id = route.carrier_id " +
                "join point fp on fp.id = route.departure_point_id " +
                "join point sp on sp.id = route.destination_point_id " +
                "where route.id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeRoute, id);
    }

    @Override
    public List<Route> getAll() {
        final String sqlQuery = "SELECT route.id, route.route_number, " +
                "fp.id fpi , fp.title fpt, " +
                "sp.id spi , sp.title spt, " +
                "route.duration, " +
                "c.id carId, c.company, c.phone " +
                "FROM route " +
                "join carrier c on c.id = route.carrier_id " +
                "join point fp on fp.id = route.departure_point_id " +
                "join point sp on sp.id = route.destination_point_id";
        return jdbcTemplate.query(sqlQuery, this::makeRoute);
    }

    @Override
    public Route update(Route route) {
        final String sqlQuery = "UPDATE route " +
                "SET route_number = ?, departure_point_id = ?, destination_point_id = ?, carrier_id = ?, duration = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, route.getRouteNumber());
            stmt.setInt(2, route.getDeparturePoint().getId());
            stmt.setInt(3, route.getDestinationPoint().getId());
            stmt.setInt(4, route.getCarrier().getId());
            stmt.setObject(5, route.getDuration());
            stmt.setInt(6, route.getId());
            return stmt;
        });
        return route;
    }

    @Override
    public Point addPoint(Point point) {
        final String sqlQuery = "INSERT INTO point (title) VALUES (?)";
        KeyHolder generatedId = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, point.getTitle());
            return stmt;
        }, generatedId);
        point.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return point;
    }

    @Override
    public List<Point> getAllPoints() {
        final String sqlQuery = "SELECT id, title " +
                "FROM point " +
                "ORDER BY id";
        return jdbcTemplate.query(sqlQuery, rs -> {
            List<Point> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Point(rs.getInt("id"), rs.getString("title")));
            }
            return list;
        });
    }

    private Route makeRoute(ResultSet resultSet, int rowNum) throws SQLException {

        int id = resultSet.getInt("id");
        String routeNumber = resultSet.getString("route_number");
        Point departurePoint = new Point(resultSet.getInt("fpi"), resultSet.getString("fpt"));
        Point destinationPoint = new Point(resultSet.getInt("spi"), resultSet.getString("spt"));
        LocalTime duration = resultSet.getTime("duration").toLocalTime();
        Carrier carrier = new Carrier(resultSet.getInt("carId"),
                resultSet.getString("company"),
                resultSet.getString("phone"));
        return new Route(id, routeNumber, departurePoint, destinationPoint, carrier, duration);
    }
}
