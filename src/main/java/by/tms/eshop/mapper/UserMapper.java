package by.tms.eshop.mapper;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.UserFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "roleDto", target = "role")
    User convetrToUser(UserFormDto user);

    @Mapping(source = "role", target = "roleDto")
    UserFormDto convetrToUserFormDto(User user);
}
