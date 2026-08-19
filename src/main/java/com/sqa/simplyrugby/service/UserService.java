package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.User;
import java.util.List;

/**
 * 用户服务接口（纯文件存储版）
 */
public interface UserService {
        // 查询所有用户
        List<User> list();

        // 根据用户名查询用户
        User getByUsername(String username);

        // 添加用户
        boolean add(User user);

        // 修改用户
        boolean update(User user);

        // 删除用户
        boolean delete(String username);
}