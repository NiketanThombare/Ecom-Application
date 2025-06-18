package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(UserRequest userRequest) {
        User user = new User();
        mapToUserFromUserRequest(user,userRequest);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse).collect(toList());
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
       return userRepository.findById(id).map(this::mapToUserResponse);

    }

    @Override
    public Boolean updateUser(Long id, UserRequest updatedUserRequest) {
      return   userRepository.findById(id).map(existingUser->{
           mapToUserFromUserRequest(existingUser,updatedUserRequest);
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

    public void mapToUserFromUserRequest(User user,UserRequest userRequest){
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress()!=null){
            Address address=new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);

        }

    }

    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO  addressDTO = new AddressDTO();

            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());

            userResponse.setAddress(addressDTO);
        }
  return  userResponse;

    }
}
