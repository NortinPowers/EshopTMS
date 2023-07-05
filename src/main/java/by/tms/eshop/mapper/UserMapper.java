package by.tms.eshop.mapper;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.UserFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
public interface UserMapper {

    @Mapping(source = "roleDto", target = "role")
    User convertToUser(UserFormDto user);

    @Mapping(source = "role", target = "roleDto")
    UserFormDto convertToUserFormDto(User user);
}
