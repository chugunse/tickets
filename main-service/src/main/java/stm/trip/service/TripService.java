package stm.trip.service;

import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripPublicDto;
import stm.trip.dto.TripUpdateDto;

import java.util.List;

public interface TripService {
    TripFullDto addTrip(TripNewDto dto);

    void deleteTrip(Long id);

    TripFullDto getTripById(Long id);

    List<TripFullDto> getAllTrips();

    List<TripPublicDto> getAllTripsToPublic();

    TripFullDto updateTrip(TripUpdateDto dto, Long id);
}
