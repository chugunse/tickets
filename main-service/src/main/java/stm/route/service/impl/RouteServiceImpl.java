package stm.route.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.mapper.CarrierMapper;
import stm.carrier.model.Carrier;
import stm.carrier.storage.CarrierRepository;
import stm.exception.model.ResourceNotFoundException;
import stm.route.dto.PointDto;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RoutePatchDto;
import stm.route.mapper.PointMapper;
import stm.route.mapper.RouteMapper;
import stm.route.model.Point;
import stm.route.model.Route;
import stm.route.service.RouteService;
import stm.route.storage.RouteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final CarrierRepository carrierRepository;
    private final RouteMapper routeMapper;
    private final CarrierMapper carrierMapper;
    private final PointMapper pointMapper;

    @Override
    public RouteFullDto addRoute(RouteNewDto dto) {
        Carrier carrier;
        try {
            carrier = carrierRepository.getById(dto.getCarrierId());
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
        List<PointDto> points = getAllPoints();
        Map<Integer, PointDto> map = points.stream().collect(Collectors.toMap(PointDto::getId, pointDto -> pointDto));
        if (!map.containsKey(dto.getDeparturePoint()) || !map.containsKey(dto.getDestinationPoint())){
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

    @Override
    public void deleteRoute(int id) {
        routeRepository.deleteById(id);
    }

    @Override
    public RouteFullDto getRouteById(int id) {
        return routeMapper.toRouteFullDto(getById(id));
    }

    @Override
    public List<RouteFullDto> getAllRoutes() {
        return routeMapper.toRouteFullDtoList(routeRepository.getAll());
    }

    @Override
    public RouteFullDto patchRoute(int id, RoutePatchDto dto) {
        RouteFullDto old = getRouteById(id);
        List<PointDto> points = getAllPoints();
        Map<Integer, PointDto> map = points.stream().collect(Collectors.toMap(PointDto::getId, pointDto -> pointDto));
        if (dto.getDeparturePoint() != null) {
            if (map.containsKey(dto.getDeparturePoint())) {
                old.setDeparturePoint(map.get(dto.getDeparturePoint()));
            } else {
                throw new ResourceNotFoundException("пункт отправления с id= " + dto.getDeparturePoint() + "не найден");
            }
        }
        if (dto.getDestinationPoint() != null){
            if (map.containsKey(dto.getDestinationPoint())){
                old.setDestinationPoint(map.get(dto.getDestinationPoint()));
            } else {
                throw new ResourceNotFoundException("пункт назначения с id= " + dto.getDeparturePoint() + "не найден");
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

    @Override
    public PointDto addPoint(PointDto dto) {
        Point point = routeRepository.addPoint(pointMapper.toModel(dto));
        return pointMapper.toDto(point);
    }

    @Override
    @Cacheable(value = "RouteService::getAllPoints")
    public List<PointDto> getAllPoints() {
        System.out.println(LocalDateTime.now());
        List<Point> list = routeRepository.getAllPoints();
        return pointMapper.toDtoList(list);
    }

    public Route getById(int id) {
        try {
            return routeRepository.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
