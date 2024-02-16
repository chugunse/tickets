package solds.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets_sold")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSave {
    @Id
    private Long id;
    @Column(name = "buyer")
    private String buyersFullName;
    @Column(name = "place_number")
    private Integer placeNumber;
    @Column(name = "trip_title")
    private String tripTitle;
    @Column(name = "route_number")
    private String routeNumber;
    @Column(name = "departure_point")
    private String departurePoint;
    @Column(name = "destination_point")
    private String destinationPoint;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private int price;
    private String carrier;
    private LocalDateTime timestamp;
}
