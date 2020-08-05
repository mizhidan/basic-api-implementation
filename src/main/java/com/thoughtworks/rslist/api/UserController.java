package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    public List<User> getUserList() {
        return userList;
    }
    private static List<User> userList = new ArrayList<>();

    public void initUserList() {
        userList.add(new User("dj", "male", 22, "abc@bac.com", "12345678911"));
    }

    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user) {
        userList.add(user);
        return ResponseEntity.created(null).header(userList.size() + "").build();
    }

    @GetMapping("/user/list")
    public ResponseEntity getMappingUserList() {
        return ResponseEntity.ok(userList);
    }
}
