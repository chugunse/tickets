package stm.trip.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import stm.route.model.Route;

import java.time.LocalDateTime;

@Data
@Builder
public class Trip {
    private Long id;
    private String title;
    private Route route;
    private LocalDateTime dateTime;
    private int price;
    private int amount;
}
