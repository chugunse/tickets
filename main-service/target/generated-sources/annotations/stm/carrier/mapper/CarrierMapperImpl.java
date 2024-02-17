package stm.carrier.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.model.Carrier;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-17T13:06:02+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
@Component
public class CarrierMapperImpl implements CarrierMapper {

    @Override
    public Carrier toCarrierModel(CarrierDto dto) {
        if ( dto == null ) {
            return null;
        }

        Carrier.CarrierBuilder carrier = Carrier.builder();

        carrier.company( dto.getCompany() );
        carrier.phone( dto.getPhone() );

        return carrier.build();
    }

    @Override
    public CarrierDto toCarrierDto(Carrier model) {
        if ( model == null ) {
            return null;
        }

        CarrierDto carrierDto = new CarrierDto();

        carrierDto.setCompany( model.getCompany() );
        carrierDto.setPhone( model.getPhone() );

        return carrierDto;
    }

    @Override
    public List<CarrierDto> toCarrierDtoList(List<Carrier> list) {
        if ( list == null ) {
            return null;
        }

        List<CarrierDto> list1 = new ArrayList<CarrierDto>( list.size() );
        for ( Carrier carrier : list ) {
            list1.add( toCarrierDto( carrier ) );
        }

        return list1;
    }

    @Override
    public List<CarrierFullDto> toCarrierFullDtoList(List<Carrier> list) {
        if ( list == null ) {
            return null;
        }

        List<CarrierFullDto> list1 = new ArrayList<CarrierFullDto>( list.size() );
        for ( Carrier carrier : list ) {
            list1.add( toCarrierFullDto( carrier ) );
        }

        return list1;
    }

    @Override
    public CarrierFullDto toCarrierFullDto(Carrier carrier) {
        if ( carrier == null ) {
            return null;
        }

        CarrierFullDto carrierFullDto = new CarrierFullDto();

        carrierFullDto.setId( carrier.getId() );
        carrierFullDto.setCompany( carrier.getCompany() );
        carrierFullDto.setPhone( carrier.getPhone() );

        return carrierFullDto;
    }

    @Override
    public Carrier toCarrierModel(CarrierFullDto dto) {
        if ( dto == null ) {
            return null;
        }

        Carrier.CarrierBuilder carrier = Carrier.builder();

        carrier.id( dto.getId() );
        carrier.company( dto.getCompany() );
        carrier.phone( dto.getPhone() );

        return carrier.build();
    }
}
