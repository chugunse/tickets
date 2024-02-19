package stm.carrier.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import stm.carrier.dto.CarrierDto;
import stm.carrier.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("carriers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "публичный контроллер перевозчиков")
public class CarrierControllerPublic {
    private final CarrierService carrierService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "посмотреть всех перевозчиков")
    public List<CarrierDto> getAllCarriers() {
        log.info("get: getAllCarriers");
        return carrierService.getAllCarriers();
    }
}
