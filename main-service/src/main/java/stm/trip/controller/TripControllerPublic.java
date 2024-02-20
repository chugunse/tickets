package stm.trip.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import stm.trip.dto.TripPublicDto;
import stm.trip.service.impl.TripServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Tag(name = "контроллер рейсов")
public class TripControllerPublic {
    private final TripServiceImpl tripService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "просмотр рейсов")
    @ApiResponse(responseCode = "200", description = "Ok")
    public List<TripPublicDto> getAllTrips() {
        return tripService.getAllTripsToPublic();
    }
}



