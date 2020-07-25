package com.amazon.review.service.impl;

import com.amazon.review.model.Role;
import com.amazon.review.repository.RoleRepository;
import com.amazon.review.service.RoleService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> saveAll(Set<Role> roles) {
        return roleRepository.saveAll(roles);
    }

    @Override
    public Role findByRoleName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
