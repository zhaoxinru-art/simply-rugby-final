package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.User;
import com.sqa.simplyrugby.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/get/{username}")
    public User getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/delete/{username}")
    public boolean delete(@PathVariable String username) {
        return userService.delete(username);
    }
}