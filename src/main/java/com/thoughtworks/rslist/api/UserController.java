package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    public List<User> getUserList() {
        return userList;
    }
    private static List<User> userList = new ArrayList<>();
    private static List<UserDto> userDtoList = new ArrayList<>();

    public void initUserList() {
        userList.add(new User("dj", "male", 22, "abc@bac.com", "12345678911"));
    }

    @PostMapping("/user")
    public void registerUser(@RequestBody @Valid User user) {
        UserDto userDto = new UserDto();
        userDto.setPhone(user.getPhone());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUsername());
        userDto.setVoteNum(user.getVoteNum());
        userRepository.save(userDto);
    }


    @GetMapping("/user/list")
    public ResponseEntity getMappingUserList() {
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/user/{index}")
    public ResponseEntity getUserById(@PathVariable int index) {
        userDtoList = userRepository.findAll();
        return ResponseEntity.ok().body(userDtoList.get(index - 1));
    }

    @DeleteMapping("/user/{index}")
    public ResponseEntity deleteUserById(@PathVariable int index) {
        userRepository.deleteById(index);
        return ResponseEntity.ok().build();
    }
}
