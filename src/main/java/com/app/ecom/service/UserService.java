package com.app.ecom.service;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void saveUser(UserRequest user);
    public List<UserResponse> getAllUsers();
    public Optional<UserResponse> getUserById(Long id);

    Boolean updateUser(Long id, UserRequest updatedUserRequest);

   Boolean deleteUser(Long id);
}

