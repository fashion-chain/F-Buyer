package com.hottop.backstage.role.service;

import com.hottop.core.model.user.Role;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.RoleRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService extends EntityBaseService<Role, Long> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public EntityBaseRepository<Role, Long> repository() {
        return roleRepository;
    }



}
