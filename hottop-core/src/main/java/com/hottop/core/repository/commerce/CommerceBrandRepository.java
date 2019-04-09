package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommerceBrandRepository extends EntityBaseRepository<CommerceBrand, Long> {
    @Transactional(readOnly = true)
    CommerceBrand findByName(String name);

    Page<CommerceBrand> findAllByCountry(String country, Pageable pageable);
}