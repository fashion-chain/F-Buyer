package com.hottop.core.repository.user;

import com.hottop.core.model.user.UserAddress;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends EntityBaseRepository<UserAddress, Long> {

    //
    int countById(Long id);

    //根据userId查询所有的地址
    List<UserAddress> findByUserIdAndIdNot(Long userId, Long id);
}
