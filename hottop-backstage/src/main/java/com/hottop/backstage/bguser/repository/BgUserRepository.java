package com.hottop.backstage.bguser.repository;

import com.hottop.backstage.bguser.model.BgUser;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgUserRepository extends EntityBaseRepository<BgUser, Long> {

    BgUser findByTel(String tel);

    int countByTel(String tel);

    int countById(Long id);

    BgUser findByUsername(String userName);
}
