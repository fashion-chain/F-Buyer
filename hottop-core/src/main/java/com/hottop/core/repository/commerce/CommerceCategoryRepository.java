package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommerceCategory;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 品类 repository
 */
@Repository
public interface CommerceCategoryRepository extends EntityBaseRepository<CommerceCategory, Long> {

    List<CommerceCategory> findAllByDeleteTimeIsNull();

    int countByName(String name);
}
