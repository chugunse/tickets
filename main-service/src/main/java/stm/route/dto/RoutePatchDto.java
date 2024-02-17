package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoutePatchDto {
    private String routeNumber;
    private Long departurePoint;
    private Long destinationPoint;
    private Long carrierId;
    private String duration;
}
