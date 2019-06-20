package com.hottop.core.repository.community;

import com.hottop.core.model.community.Feed;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends EntityBaseRepository<Feed, Long> {
}
