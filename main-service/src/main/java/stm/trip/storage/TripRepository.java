package stm.trip.storage;

import stm.trip.model.Trip;

import java.util.List;
import java.util.Map;

public interface TripRepository {
    Trip save(Trip trip);

    void deleteById(Long id);

    Trip getById(Long id);

    Integer getSoldCount(Long id);

    Map<Long, Integer> getAllSoldCount();

    List<Trip> getAll();
}
