package com.hottop.core.repository.doll;

import com.hottop.core.model.doll.Doll;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DollRepository extends EntityBaseRepository<Doll, Long> {
}
