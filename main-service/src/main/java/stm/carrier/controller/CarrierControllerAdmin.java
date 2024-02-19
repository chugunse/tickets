package stm.carrier.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("/admin/carriers")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "контроллер перевозчиков админстратора",
        description = "внесение изменений в фирмы перевозчики")
public class CarrierControllerAdmin {
    private final CarrierService carrierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "добавление перевозчика")
    public CarrierFullDto addCarrier(@RequestBody @Validated @Parameter(description = "dto перевозчик") CarrierDto dto) {
        log.info("post: {}", dto);
        return carrierService.addCarrier(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "удаление превозчика")
    public void deleteCarrier(@PathVariable @Parameter(description = "id удаляемого перевозчика") Long id) {
        log.info("del: {}", id);
        carrierService.deleteCarrierById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "посмотреть всех перевозчиков")
    public List<CarrierFullDto> getAllCarriers() {
        log.info("get: getAllCarriers");
        return carrierService.getAllFullCarriers();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "внести изменения в данные о перевозчике")
    public CarrierFullDto patchCarrier(@PathVariable @Parameter(description = "id перевозчика") Long id,
                                       @RequestBody @Validated
                                       @Parameter(description = "dto обновленного перевозчика") CarrierFullDto dto) {
        log.info("patch: carrierId = {}, dto = {}", id, dto);
        return carrierService.updateCarrier(id, dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "посмотреть информацию об определенном перевозчике")
    public CarrierFullDto getCarrierById(@PathVariable @Parameter(description = "id перевозчика") Long id) {
        log.info("get: id = {}", id);
        return carrierService.getCarrierById(id);
    }
}
