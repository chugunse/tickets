package stm.route.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.mapper.CarrierMapper;
import stm.carrier.model.Carrier;
import stm.carrier.storage.CarrierRepository;
import stm.exception.model.ResourceNotFoundException;
import stm.route.dto.PointDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RoutePatchDto;
import stm.route.mapper.PointMapper;
import stm.route.mapper.RouteMapper;
import stm.route.model.Point;
import stm.route.model.Route;
import stm.route.service.RouteService;
import stm.route.storage.RouteRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final CarrierRepository carrierRepository;
    private final RouteMapper routeMapper;
    private final CarrierMapper carrierMapper;
    private final PointMapper pointMapper;

    @Override
    @Transactional
    public RouteFullDto addRoute(RouteNewDto dto) {
        Carrier carrier;
        try {
            carrier = carrierRepository.getById(dto.getCarrierId());
        } catch (EmptyResultDataAccessException exception) {
            log.warn("для маршрута превозчик с id = {} не найден", dto.getCarrierId());
            throw new ResourceNotFoundException("для маршрута превозчик с id = " + dto.getCarrierId() + " не найден");
        }
        List<PointDto> points = getAllPoints();
        Map<Long, PointDto> map = points.stream().collect(Collectors.toMap(PointDto::getId, pointDto -> pointDto));
        if (!map.containsKey(dto.getDeparturePoint()) || !map.containsKey(dto.getDestinationPoint())) {
            log.warn("пункт отправления или назначения не найден");
            throw new ResourceNotFoundException("пункт отправления или назначения не найден");
        }
        Route route = routeMapper.toRouteModel(dto);
        Point departurePoint = pointMapper.toModel(map.get(dto.getDeparturePoint()));
        Point destination = pointMapper.toModel(map.get(dto.getDestinationPoint()));
        route.setDeparturePoint(departurePoint);
        route.setDestinationPoint(destination);
        route.setCarrier(carrier);
        route.setId(routeRepository.save(route));
        return routeMapper.toRouteFullDto(route);
    }

    @Transactional
    @Override
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public RouteFullDto getRouteById(Long id) {
        return routeMapper.toRouteFullDto(getById(id));
    }

    @Transactional
    @Override
    public List<RouteFullDto> getAllRoutes() {
        return routeMapper.toRouteFullDtoList(routeRepository.getAll());
    }

    @Transactional
    @Override
    public RouteFullDto patchRoute(Long id, RoutePatchDto dto) {
        RouteFullDto old = getRouteById(id);
        List<PointDto> points = getAllPoints();
        Map<Long, PointDto> map = points.stream().collect(Collectors.toMap(PointDto::getId, pointDto -> pointDto));
        if (dto.getDeparturePoint() != null) {
            if (map.containsKey(dto.getDeparturePoint())) {
                old.setDeparturePoint(map.get(dto.getDeparturePoint()));
            } else {
                log.warn("пункт отправления с id= {} не найден", dto.getDeparturePoint());
                throw new ResourceNotFoundException("пункт отправления с id= " + dto.getDeparturePoint() + "не найден");
            }
        }
        if (dto.getDestinationPoint() != null) {
            if (map.containsKey(dto.getDestinationPoint())) {
                old.setDestinationPoint(map.get(dto.getDestinationPoint()));
            } else {
                log.warn("пункт назначения с id= {} не найден", dto.getDestinationPoint());
                throw new ResourceNotFoundException("пункт назначения с id= " + dto.getDestinationPoint() + "не найден");
            }
        }
        ofNullable(dto.getDuration()).ifPresent(old::setDuration);
        ofNullable(dto.getRouteNumber()).ifPresent(old::setRouteNumber);
        if (dto.getCarrierId() != null) {
            CarrierFullDto carrierFullDto = carrierMapper
                    .toCarrierFullDto(carrierRepository.getById(dto.getCarrierId()));
            old.setCarrier(carrierFullDto);
        }
        routeRepository.update(routeMapper.toRouteModel(old));
        return old;
    }

    @Transactional
    @Override
    public PointDto addPoint(PointDto dto) {
        Point point = routeRepository.addPoint(pointMapper.toModel(dto));
        return pointMapper.toDto(point);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PointDto> getAllPoints() {
        log.debug("берем points из бд");
        List<Point> list = routeRepository.getAllPoints();
        return pointMapper.toDtoList(list);
    }

    @Transactional
    public Route getById(Long id) {
        try {
            return routeRepository.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("маршрут с id = {} не найдена", id);
            throw new ResourceNotFoundException("маршрут с id = " + id + " не найдена");
        }
    }
}
