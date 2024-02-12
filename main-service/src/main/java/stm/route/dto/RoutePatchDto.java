package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoutePatchDto {
    private String routeNumber;
    private Integer departurePoint;
    private Integer destinationPoint;
    private Integer carrierId;
    private String duration;
}
