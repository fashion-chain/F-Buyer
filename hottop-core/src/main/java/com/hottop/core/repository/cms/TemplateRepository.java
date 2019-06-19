package com.hottop.core.repository.cms;

import com.hottop.core.model.cms.Template;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends EntityBaseRepository<Template, Long> {
    Template findByName(String name);
}
