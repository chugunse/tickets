package stm.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserNewDto dto);

    UserDto toUserDto(User model);
}
