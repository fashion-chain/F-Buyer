package com.hottop.core.repository.community;

import com.hottop.core.model.community.Community;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends EntityBaseRepository<Community, Long> {
}
