package stm.route.storage;

import stm.route.model.Point;
import stm.route.model.Route;

import java.util.List;

public interface RouteRepository {
    Long save(Route route);

    void deleteById(Long id);

    Route getById(Long id);

    List<Route> getAll();

    Route update(Route route);

    Point addPoint(Point toModel);

    List<Point> getAllPoints();
}
