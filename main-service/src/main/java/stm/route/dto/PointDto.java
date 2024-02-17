package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class PointDto implements Serializable {
    private Long id;
    private String title;
}
