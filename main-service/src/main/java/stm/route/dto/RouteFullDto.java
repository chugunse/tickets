package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.carrier.dto.CarrierFullDto;

@NoArgsConstructor
@Getter
@Setter
public class RouteFullDto {
    private int id;
    private String routeNumber;
    private PointDto departurePoint;
    private PointDto destinationPoint;
    private CarrierFullDto carrier;
    private String duration;
}
