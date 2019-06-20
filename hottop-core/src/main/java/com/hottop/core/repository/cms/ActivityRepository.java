package com.hottop.core.repository.cms;

import com.hottop.core.model.cms.Activity;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends EntityBaseRepository<Activity, Long> {
}
