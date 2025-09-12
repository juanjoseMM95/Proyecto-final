package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email); //select * from user where email = <email>;
    List<UserEntity> findByName(String name); //select * from user where name = <name>;
    List<UserEntity> findAllByOrderByNameAsc();//método definido en la entidad con @query
    @Query(name = "User.findByEnterpriseId")
    UserEntity findByEnterpriseId(@Param("enterprise_id") String entreprise_id);
}

