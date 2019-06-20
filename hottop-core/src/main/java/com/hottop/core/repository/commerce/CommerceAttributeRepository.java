package com.hottop.core.repository.commerce;

import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.commerce.CommerceAttribute;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceAttributeRepository extends EntityBaseRepository<CommerceAttribute, Long> {
}
