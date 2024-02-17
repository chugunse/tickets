package stm.route.service;

import stm.route.dto.PointDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RoutePatchDto;

import java.util.List;

public interface RouteService {

    RouteFullDto addRoute(RouteNewDto dto);

    void deleteRoute(Long id);

    RouteFullDto getRouteById(Long id);

    List<RouteFullDto> getAllRoutes();

    RouteFullDto patchRoute(Long id, RoutePatchDto dto);

    PointDto addPoint(PointDto dto);

    List<PointDto> getAllPoints();
}
