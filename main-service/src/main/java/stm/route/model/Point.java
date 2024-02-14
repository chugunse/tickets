package stm.route.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Point implements Serializable {
    private int id;
    private String title;
}
