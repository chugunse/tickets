package stm.user.mapper;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T16:33:12+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.18 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserNewDto dto) {
        if ( dto == null ) {
            return null;
        }

        String fullName = null;
        String login = null;
        String password = null;

        fullName = dto.getFullName();
        login = dto.getLogin();
        password = dto.getPassword();

        Long id = null;

        User user = new User( id, login, password, fullName );

        return user;
    }

    @Override
    public UserDto toUserDto(User model) {
        if ( model == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( model.getId() );
        userDto.setLogin( model.getLogin() );
        userDto.setFullName( model.getFullName() );

        return userDto;
    }
}
