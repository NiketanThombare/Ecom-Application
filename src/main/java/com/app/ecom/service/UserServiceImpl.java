package com.app.ecom.service;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.mapper.UserMapper;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void saveUser(UserRequest request) {
        userRepository.save(userMapper.toUser(new User(), request));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
       return userRepository.findById(id).map(userMapper::toUserResponse);
    }

    @Override
    public Boolean updateUser(Long id, UserRequest request) {
      return userRepository.findById(id).map(existingUser->{
         // toUser(existingUser,request);
          userMapper.updateUser(existingUser,request);
          userRepository.save(existingUser);
            return true;
        }).orElse(false);

    }

    @Override
    public Boolean deleteUser(Long id) {
    return userRepository.findById(id).map(user -> {
         userRepository.delete(user);
         return true;
     }).orElse(false);

    }



}
