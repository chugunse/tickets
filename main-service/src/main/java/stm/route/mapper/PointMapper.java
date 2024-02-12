package stm.route.mapper;

import org.mapstruct.Mapper;
import stm.route.dto.PointDto;
import stm.route.model.Point;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {
    Point toModel(PointDto dto);
    PointDto toDto(Point model);

    List<PointDto> toDtoList(List<Point> list);
}
