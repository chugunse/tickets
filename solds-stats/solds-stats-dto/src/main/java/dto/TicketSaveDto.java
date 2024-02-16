package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSaveDto implements Serializable {
    private Long id;
    private String buyersFullName;
    private Integer placeNumber;
    private String tripTitle;
    private String routeNumber;
    private String departurePoint;
    private String destinationPoint;
    private LocalDateTime dateTime;
    private int price;
    private String carrier;
    private LocalDateTime timestamp;
}
