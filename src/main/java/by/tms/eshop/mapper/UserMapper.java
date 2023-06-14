package by.tms.eshop.mapper;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.UserFormDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convetrToUser(UserFormDto user);
}
