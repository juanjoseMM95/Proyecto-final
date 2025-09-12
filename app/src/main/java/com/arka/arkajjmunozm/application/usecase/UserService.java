
package com.arka.arkajjmunozm.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.arka.arkajjmunozm.domain.model.User;
import com.arka.arkajjmunozm.domain.port.in.IUserService;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;

    private User mapToUser(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setEnterprise_id(entity.getEnterprise_id());
        return user;
    }

    private UserEntity mapToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setEnterprise_id(user.getEnterprise_id());
        return entity;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersFilterByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersOrderByName() {
        return userRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    @Override
    public User newUser(UserEntity user) {
        UserEntity savedEntity = userRepository.save(user);
        return mapToUser(savedEntity);
    }

    @Override
    public User getUserByEnterpriseId(String enterpriseId) {
        UserEntity entity = userRepository.findByEnterpriseId(enterpriseId);
        return entity != null ? mapToUser(entity) : null;
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id)
                .map(this::mapToUser)
                .orElse(null);
    }

    @Override
    public User updateUser(int id, UserEntity user) {
        Optional<UserEntity> userExist = userRepository.findById(id);
        if (userExist.isPresent()) {
            user.setId(id);
            UserEntity updatedEntity = userRepository.save(user);
            return mapToUser(updatedEntity);
        }
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        Optional<UserEntity> userExist = userRepository.findById(id);
        if (userExist.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}