package stm.carrier.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.model.Carrier;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarrierMapper {
    @Mapping(target = "id", ignore = true)
    Carrier toCarrierModel(CarrierDto dto);

    CarrierDto toCarrierDto(Carrier model);

    List<CarrierDto> toCarrierDtoList(List<Carrier> list);

    List<CarrierFullDto> toCarrierFullDtoList(List<Carrier> list);

    CarrierFullDto toCarrierFullDto(Carrier carrier);

    Carrier toCarrierModel(CarrierFullDto dto);
}
