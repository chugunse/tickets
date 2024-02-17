package stm.carrier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class Carrier implements Serializable {
    private Long id;
    private final String company;
    private final String phone;
}
