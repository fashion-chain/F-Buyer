package com.hottop.core.repository.user;

import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
<<<<<<< HEAD
=======
import org.springframework.data.jpa.repository.Query;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends EntityBaseRepository<User, Long> {

<<<<<<< HEAD
    List<User> findByTel(String tel);
=======
    User findByTel(String tel);

    User findByTelAndPassword(String tel, String password);

    Long countById(Long id);

    User findByUsername(String username);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
