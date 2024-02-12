package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RouteNewDto {
    private String routeNumber;
    private int departurePoint;
    private int destinationPoint;
    private int carrierId;
    private String duration;
}
