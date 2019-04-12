package com.hottop.core.repository.user;

import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends EntityBaseRepository<User, Long> {

    User findByTel(String tel);

    User findByTelAndPassword(String tel, String password);

    Long countById(Long id);

    User findByUsername(String username);
}
