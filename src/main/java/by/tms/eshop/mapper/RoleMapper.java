package by.tms.eshop.mapper;

import by.tms.eshop.domain.Role;
import by.tms.eshop.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper(config = CustomMapperConfig.class)
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleDto convertToRoleDto(Role role);

    Role convertToRole(RoleDto roleDto);
}
