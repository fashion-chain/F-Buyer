package com.hottop.core.repository.user;

import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends EntityBaseRepository<User, Long> {

    List<User> findByTel(String tel);
}
