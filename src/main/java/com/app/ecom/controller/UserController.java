package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
               // return ResponseEntity.ok().body(userService.getAllUsers());


    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
       return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.saveUser(userRequest);
        return ResponseEntity.ok("User Added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserRequest updatedUserRequest){
Boolean updated=userService.updateUser(id,updatedUserRequest);
if (updated){
return ResponseEntity.ok("User Updated successfully");
}
return ResponseEntity.notFound().build();
    }

@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
       Boolean deleted= userService.deleteUser(id);
       if (deleted){
           return ResponseEntity.ok("User Deleted Successfully");
       }
       return ResponseEntity.notFound().build();
    }
}
