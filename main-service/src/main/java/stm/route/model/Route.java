package stm.route.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import stm.carrier.model.Carrier;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class Route implements Serializable {
    private int id;
    private String routeNumber;
    private Point departurePoint;
    private Point destinationPoint;
    private Carrier carrier;
    private LocalTime duration;
}