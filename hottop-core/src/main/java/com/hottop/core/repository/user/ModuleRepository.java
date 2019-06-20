package com.hottop.core.repository.user;

import com.hottop.core.model.user.Module;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends EntityBaseRepository<Module, Long> {

    @Override
    List<Module> findAllById(Iterable<Long> longs);

    List<Module> findAllByState(String state);

    //查询用户的权限
    @Query(nativeQuery = true, value = "select gb_module.* from (select * from gb_user_module where gb_user_id = ?1) tmp left join gb_module on tmp.gb_module_id = gb_module.id;")
    List<Module> findAllByUserId(Long userId);

    //查询application 下的模块modules
    @Query(nativeQuery = true, value = "select gb_module.* from (select * from gb_application_module where gb_application_id = ?1) tmp left join gb_module on tmp.gb_module_id = gb_module.id;")
    List<Module> findAllByAppId(Long bgUserId) ;
}
