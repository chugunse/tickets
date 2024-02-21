package stm.ticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import stm.exception.model.ApiError;
import stm.ticket.dto.TicketDto;
import stm.ticket.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;

import static stm.util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "контроллер билетов", description = "поиск доступных билетов и их покупка")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "поиск билетов",
            description = "поиск всех доступных к покупке билетов по параметрам поиска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public List<TicketDto> getAllTickets(@RequestParam(required = false)
                                         @DateTimeFormat(pattern = DATE_TIME_PATTERN)
                                         @Parameter(description = "начало даты поиска", example = "yyyy-MM-dd HH:mm")
                                         LocalDateTime rangeStart,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = DATE_TIME_PATTERN)
                                         @Parameter(description = "конец даты поиска", example = "yyyy-MM-dd HH:mm")
                                         LocalDateTime rangeEnd,
                                         @RequestParam(required = false)
                                         @Parameter(description = "id точка отпавления") Long departurePointId,
                                         @RequestParam(required = false)
                                         @Parameter(description = "название точки отправления") String departurePoint,
                                         @RequestParam(required = false)
                                         @Parameter(description = "id точка назначения") Long destinationPointId,
                                         @RequestParam(required = false)
                                         @Parameter(description = "id точка назначения") String destinationPoint,
                                         @RequestParam(required = false)
                                         @Parameter(description = "название превозчика") String carrier,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("get запрос билетов: время от {}, время до {}, откуда id {}, откуда {}, куда id {}, куда {}," +
                        "комапния {}, from {}, size {}",
                rangeStart, rangeEnd, departurePointId, departurePoint, destinationPointId, destinationPoint,
                carrier, from, size);
        return ticketService.getAllTickets(rangeStart, rangeEnd,
                departurePointId, departurePoint,
                destinationPointId, destinationPoint,
                carrier, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "покупка билета",
            description = "купить определенный билет по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Not found", content = {
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
    public TicketDto buyTicket(@RequestParam @Parameter(description = "id билета") Long ticketId,
                               HttpServletRequest request) {
        log.info("post: купить билет = {}", ticketId);
        return ticketService.buyTicket(ticketId, request);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "просмотр купленных билетов",
            description = "посмотреть купленные билеты определенного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
            })
    })
    public List<TicketDto> getUserTickets(HttpServletRequest request) {
        log.info("get: запрос купленных билетов");
        return ticketService.getUserTickets(request);
    }
}
