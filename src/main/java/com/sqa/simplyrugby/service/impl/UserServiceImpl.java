package com.sqa.simplyrugby.service.impl;

import com.sqa.simplyrugby.entity.User;
import com.sqa.simplyrugby.service.UserService;
import com.sqa.simplyrugby.utils.JsonUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_PATH = "users.json";

    @Override
    public List<User> list() {
        return JsonUtil.readList(User.class, FILE_PATH);
    }

    @Override
    public User getByUsername(String username) {
        return list().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(User user) {
        List<User> list = list();
        boolean exists = list.stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername()));
        if (exists) return false;

        Long maxId = list.stream()
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(0L);
        user.setId(maxId + 1);

        list.add(user);
        JsonUtil.writeList(list, FILE_PATH);
        return true;
    }

    @Override
    public boolean update(User user) {
        List<User> list = list();
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (u.getUsername().equals(user.getUsername())) {
                user.setId(u.getId());
                list.set(i, user);
                JsonUtil.writeList(list, FILE_PATH);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String username) {
        List<User> list = list();
        list = list.stream()
                .filter(u -> !username.equals(u.getUsername()))
                .collect(Collectors.toList());
        JsonUtil.writeList(list, FILE_PATH);
        return true;
    }
}