package com.amazon.review.service;

import com.amazon.review.model.Role;

public interface RoleService extends GenericService<Role> {

    Role findByRoleName(Role.RoleName roleName);
}
