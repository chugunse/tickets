package stm.trip.controller;

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
import stm.exception.model.ApiError;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripUpdateDto;
import stm.trip.service.impl.TripServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin/trips")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "контроллер рейсов",
        description = "управление рейсами")
public class TripControllerAdmin {
    private final TripServiceImpl tripService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "добавление нового рейса")
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
    public TripFullDto addTrip(@RequestBody @Validated @Parameter(description = "dto нового рейса") TripNewDto dto) {
        log.info("post: добавить рейс {}", dto);
        return tripService.addTrip(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "удаление рейса")
    @ApiResponse(responseCode = "204", description = "No Content")
    public void deleteTrip(@PathVariable @Parameter(description = "id рейса") Long id) {
        log.info("delet: рейс с id = {}", id);
        tripService.deleteTrip(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "получение информации об определенном рейсе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public TripFullDto getTripById(@PathVariable @Parameter(description = "id рейса") Long id) {
        log.info("get: рейс id = {}", id);
        return tripService.getTripById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "получить информацию обо всех рейсах")
    @ApiResponse(responseCode = "200", description = "Ok")
    public List<TripFullDto> getAllTrips() {
        log.info("getAllTrips");
        return tripService.getAllTrips();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "внесение изменений в рейс")
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
    public TripFullDto patchTrip(@RequestBody @Validated @Parameter(description = "dto обновление рейса")
                                 TripUpdateDto dto, @PathVariable @Parameter(description = "id рейся для изменения")
                                 Long id) {
        log.info("patch: id {}, dto {}", id, dto);
        return tripService.updateTrip(dto, id);
    }
}
