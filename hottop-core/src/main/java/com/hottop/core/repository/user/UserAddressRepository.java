package com.hottop.core.repository.user;

import com.hottop.core.model.user.UserAddress;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends EntityBaseRepository<UserAddress, Long> {
}
