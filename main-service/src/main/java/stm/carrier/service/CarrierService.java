package stm.carrier.service;

import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;

import java.util.List;

public interface CarrierService {
    CarrierFullDto addCarrier(CarrierDto dto);

    void deleteCarrierById(Long id);

    List<CarrierFullDto> getAllFullCarriers();

    CarrierFullDto updateCarrier(Long id, CarrierFullDto dto);

    CarrierFullDto getCarrierById(Long id);

    List<CarrierDto> getAllCarriers();
}
