package com.app.ecom.mapper;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(User user, UserRequest request) {
        Address address = null;
        if (request.getAddress() != null) {
            AddressDTO addressDTO = request.getAddress();
            address = Address.builder()
                    .street(addressDTO.getStreet())
                    .city(addressDTO.getCity())
                    .state(addressDTO.getState())
                    .country(addressDTO.getCountry())
                    .zipcode(addressDTO.getZipcode())
                    .build();
        }

        return User.builder()
                .id(user.getId()) // retain existing ID during update
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                // .role(request.getRole() != null ? request.getRole() : UserRole.CUSTOMER)
                .address(address)
                .build();
    }

    public UserResponse toUserResponse(User user) {
        AddressDTO addressDTO = null;
        if (user.getAddress() != null) {
            Address address = user.getAddress();
            addressDTO = AddressDTO.builder()
                    .street(address.getStreet())
                    .city(address.getCity())
                    .state(address.getState())
                    .country(address.getCountry())
                    .zipcode(address.getZipcode())
                    .build();
        }

        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .address(addressDTO)
                .build();
    }

    public void updateUser(User existingUser, UserRequest request) {
        if (request.getFirstName() != null) existingUser.setFirstName(request.getFirstName());
        if (request.getLastName() != null) existingUser.setLastName(request.getLastName());
        if (request.getEmail() != null) existingUser.setEmail(request.getEmail());
        if (request.getPhone() != null) existingUser.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            AddressDTO dto = request.getAddress();
            Address address = Address.builder()
                    .street(dto.getStreet())
                    .city(dto.getCity())
                    .state(dto.getState())
                    .country(dto.getCountry())
                    .zipcode(dto.getZipcode())
                    .build();
            existingUser.setAddress(address);
        }
        // ⚠️ You can optionally allow updating role, if needed.
    }

}
