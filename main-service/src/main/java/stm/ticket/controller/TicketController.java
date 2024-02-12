package stm.ticket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stm.ticket.dto.TicketDto;
import stm.ticket.service.TicketService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static stm.util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketDto> getAllTickets(@RequestParam(required = false)
                              @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                         @RequestParam(required = false)
                              @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                         @RequestParam(required = false) Integer departurePointId,
                                         @RequestParam(required = false) String departurePoint,
                                         @RequestParam(required = false) Integer destinationPointId,
                                         @RequestParam(required = false) String destinationPoint,
                                         @RequestParam(required = false) String carrier,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("запрос доступных билетов");
        return ticketService.getAllTickets(rangeStart, rangeEnd,
                departurePointId, departurePoint,
                destinationPointId, destinationPoint,
                carrier, from, size);
    }

//    @GetMapping
}
