package com.hottop.core.repository.user;

import com.hottop.core.model.user.Role;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends EntityBaseRepository<Role, Long> {

    @Override
    List<Role> findAllById(Iterable<Long> longs);
}
