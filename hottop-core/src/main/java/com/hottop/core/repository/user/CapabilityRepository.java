package com.hottop.core.repository.user;

import com.hottop.core.model.user.Capability;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapabilityRepository extends EntityBaseRepository<Capability,Long> {
}
