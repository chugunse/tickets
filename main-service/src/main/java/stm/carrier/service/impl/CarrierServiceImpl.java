package stm.carrier.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.mapper.CarrierMapper;
import stm.carrier.model.Carrier;
import stm.carrier.service.CarrierService;
import stm.carrier.storage.CarrierRepository;
import stm.exception.model.ConflictException;
import stm.exception.model.ResourceNotFoundException;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarrierServiceImpl implements CarrierService {
    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    @Transactional
    @Override
    public CarrierFullDto addCarrier(CarrierDto dto) {
        try {
            Carrier carrier = carrierRepository.save(carrierMapper.toCarrierModel(dto));
            return carrierMapper.toCarrierFullDto(carrier);
        } catch (DuplicateKeyException e) {
            log.warn("компания {} или ее номер телефона {} уже используются в базе" ,dto.getCompany(), dto.getPhone());
            throw new ConflictException("компания '" + dto.getCompany() + "' или ее номер телефона '" +
                    dto.getPhone() + " уже используются в базе");
        }
    }

    @Transactional
    @Override
    public void deleteCarrierById(Long id) {
        try {
            carrierRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("компания с id = {} не найдена", id);
            throw new ResourceNotFoundException("компания с id = " + id + " не найдена");
        }
    }

    @Transactional
    @Override
    public List<CarrierFullDto> getAllFullCarriers() {
        return carrierMapper.toCarrierFullDtoList(carrierRepository.getAll());
    }

    @Transactional
    @Override
    public CarrierFullDto updateCarrier(Long id, CarrierFullDto dto) {
        CarrierFullDto carrierFullDtoFromDB = getCarrierById(id);
        ofNullable(dto.getCompany()).ifPresent(carrierFullDtoFromDB::setCompany);
        ofNullable(dto.getPhone()).ifPresent(carrierFullDtoFromDB::setPhone);
        carrierRepository.update(carrierMapper.toCarrierModel(carrierFullDtoFromDB));
        return carrierFullDtoFromDB;
    }

    @Transactional
    @Override
    public CarrierFullDto getCarrierById(Long id) {
        try {
            return carrierMapper.toCarrierFullDto(carrierRepository.getById(id));
        } catch (EmptyResultDataAccessException exception) {
            log.warn("компания с id = {} не найдена", id);
            throw new ResourceNotFoundException("компания с id = " + id + " не найдена");
        }
    }

    @Transactional
    @Override
    public List<CarrierDto> getAllCarriers() {
        return carrierMapper.toCarrierDtoList(carrierRepository.getAll());
    }
}
