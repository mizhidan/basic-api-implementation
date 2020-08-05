package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
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
    @PostMapping("/user")
    public void registerUser(@RequestBody @Valid User user) {
        userList.add(user);
    }

    public static void addUser(User user) {
        userList.add(user);
    }

    @GetMapping("/user/list")
    public List<User> getMappingUserList() {
        return userList;
    }
}
