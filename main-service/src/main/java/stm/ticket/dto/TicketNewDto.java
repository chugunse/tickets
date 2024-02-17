package stm.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketNewDto {
    private String trip;
    private Long route;
    private String dateTime;
    private int price;
}
