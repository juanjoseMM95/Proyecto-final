package com.arka.arkajjmunozm.domain.port.out;

import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.UserEntity;

import java.util.List;

public interface IUserRepository {
    UserEntity findByEmail(String email);
    List<UserEntity> findByName(String name);
    List<UserEntity> findAllByOrderByNameAsc();
    UserEntity findByEnterpriseId(String enterprise_id);
}