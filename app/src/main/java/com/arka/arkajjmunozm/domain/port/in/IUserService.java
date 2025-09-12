package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.model.User;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<User> allUsers();
    List<User> getUsersFilterByName(String name);
    List<User> getUsersOrderByName();
    User newUser(UserEntity user);
    User getUserByEnterpriseId(String enterpriseId);
    User getUser(int id);
    User updateUser(int id, UserEntity user);
    boolean deleteUser(int id);
}
