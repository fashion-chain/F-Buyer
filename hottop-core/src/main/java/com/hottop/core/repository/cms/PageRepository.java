package com.hottop.core.repository.cms;

import com.hottop.core.model.cms.Page;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends EntityBaseRepository<Page, Long> {
    Page findByName(String name);
}
