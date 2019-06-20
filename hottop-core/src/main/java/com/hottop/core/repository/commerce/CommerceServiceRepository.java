package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommerceService;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommerceServiceRepository extends EntityBaseRepository<CommerceService, Long>{

    List<CommerceService> findAllByDeleteTimeIsNull();
}
