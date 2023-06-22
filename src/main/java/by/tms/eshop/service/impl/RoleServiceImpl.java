package by.tms.eshop.service.impl;

import by.tms.eshop.domain.Role;
import by.tms.eshop.dto.RoleDto;
import by.tms.eshop.mapper.RoleMapper;
import by.tms.eshop.repository.RoleRepository;
import by.tms.eshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getRole(String role) {
        Role userRole = roleRepository.findRoleByRole(role)
                                      .orElseThrow(() -> new RuntimeException("Role not found"));
        return roleMapper.convertToRoleDto(userRole);
    }
}
