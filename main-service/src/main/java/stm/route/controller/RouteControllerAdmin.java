package stm.route.controller;

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
import stm.route.dto.PointDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RoutePatchDto;
import stm.route.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/admin/routes")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "контроллер маршрутов",
        description = "управление маршрутами")
public class RouteControllerAdmin {
    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "добавление нового маршрута")
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
    public RouteFullDto addRoute(@RequestBody @Validated
                                 @Parameter(description = "dto нового маршрута") RouteNewDto dto) {
        log.info("post route: {}", dto);
        return routeService.addRoute(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "удаление маршрута")
    @ApiResponse(responseCode = "204", description = "No Content", content = {
            @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
    })
    public void deleteRoute(@PathVariable @Parameter(description = "id удаляемого маршрута") Long id) {
        log.info("delet route: id = {}", id);
        routeService.deleteRoute(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "просмотр информации об определенном маршруте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public RouteFullDto getRouteById(@PathVariable @Parameter(description = "id маршрута") Long id) {
        log.info("get route: id = {}", id);
        return routeService.getRouteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "просмотр всех маршрутов")
    @ApiResponse(responseCode = "200", description = "Ok")
    public List<RouteFullDto> getAllRoutes() {
        log.info("getAllRoutes");
        return routeService.getAllRoutes();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "обновление информации о маршруте")
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
    public RouteFullDto patchRoute(@PathVariable @Parameter(description = "id маршрута") Long id,
                                   @RequestBody @Validated
                                   @Parameter(description = "dto с обновлением маршрута") RoutePatchDto dto) {
        log.info("patch route: id = {}, dto = {}", id, dto);
        return routeService.patchRoute(id, dto);
    }

    @PostMapping("/points")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "добавление отправной/конечной точки")
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
    public PointDto addPoint(@RequestBody @Validated
                             @Parameter(description = "dto отправной/конечной точки") PointDto dto) {
        log.info("post point: {}", dto);
        return routeService.addPoint(dto);
    }

    @GetMapping("/points")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "просмотр отправных/конечных точек")
    @ApiResponse(responseCode = "200", description = "Ok")
    public List<PointDto> getAllPoints() {
        log.info("getAllPoints");
        return routeService.getAllPoints();
    }
}
