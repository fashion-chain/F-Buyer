package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
@Repository
public interface CommerceBrandRepository extends EntityBaseRepository<CommerceBrand, Long> {
    @Transactional(readOnly = true)
    CommerceBrand findByName(String name);

    Page<CommerceBrand> findAllByCountry(String country, Pageable pageable);
<<<<<<< HEAD
=======

    List<CommerceBrand> findByCategoryIdIn(ArrayList<Long> ids);

    List<CommerceBrand> findByCategoryId(Long id);

    @Query(
            value = "select DISTINCT country from gb_commerce_brand", nativeQuery = true
    )
    List<String> findAllCountry();
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}