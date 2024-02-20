package stm.carrier.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.service.CarrierService;
import stm.exception.model.ApiError;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            }),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public CarrierFullDto addCarrier(@RequestBody @Validated @Parameter(description = "dto перевозчик") CarrierDto dto) {
        log.info("post: {}", dto);
        return carrierService.addCarrier(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "удаление превозчика")
    @ApiResponse(responseCode = "204", description = "No Content")
    public void deleteCarrier(@PathVariable @Parameter(description = "id удаляемого перевозчика") Long id) {
        log.info("del: {}", id);
        carrierService.deleteCarrierById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "посмотреть всех перевозчиков")
    @ApiResponse(responseCode = "200", description = "Ok")
    public List<CarrierFullDto> getAllCarriers() {
        log.info("get: getAllCarriers");
        return carrierService.getAllFullCarriers();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "внести изменения в данные о перевозчике")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            }),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public CarrierFullDto patchCarrier(@PathVariable @Parameter(description = "id перевозчика") Long id,
                                       @RequestBody @Validated
                                       @Parameter(description = "dto обновленного перевозчика") CarrierFullDto dto) {
        log.info("patch: carrierId = {}, dto = {}", id, dto);
        return carrierService.updateCarrier(id, dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "посмотреть информацию об определенном перевозчике")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public CarrierFullDto getCarrierById(@PathVariable @Parameter(description = "id перевозчика") Long id) {
        log.info("get: id = {}", id);
        return carrierService.getCarrierById(id);
    }
}
