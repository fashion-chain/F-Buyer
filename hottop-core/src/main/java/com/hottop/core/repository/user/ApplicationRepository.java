package com.hottop.core.repository.user;

import com.hottop.core.model.user.Application;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends EntityBaseRepository<Application, Long> {

    Application findByTel(String tel);

    int countByTel(String tel);

    int countById(Long id);

    Application findByUsername(String userName);

    //判断用户名是否已经存在
    int countByUsername(String username);
}
