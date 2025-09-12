package com.arka.arkajjmunozm.infraestructure.adapter.in.web;

import com.arka.arkajjmunozm.application.usecase.UserService;
import com.arka.arkajjmunozm.domain.model.User;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserId(@PathVariable("id") int urlVar) {
        User user = userService.getUser(urlVar);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/filtername/{param}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable String param) {
        List<User> users = userService.getUsersFilterByName(param);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/orderByName")
    public ResponseEntity<List<User>> getUsersOrderByName(){
        List<User> users = userService.getUsersOrderByName();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    //@valid en base a las rules de la entidad user
    public ResponseEntity<User> createUser(@Valid @RequestBody UserEntity user, BindingResult result) {
        User createdUser = userService.newUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int urlVar, @Valid @RequestBody UserEntity user, BindingResult result) {
        User updatedUser = userService.updateUser(urlVar, user);
        if(updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> DeleteUser(@PathVariable("id") int urlVar) {
        Boolean deleteUser = userService.deleteUser(urlVar);
        if(!deleteUser){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
