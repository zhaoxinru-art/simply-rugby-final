package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> list();
    Role getById(Long id);
    boolean add(Role role);
    boolean update(Role role);
    boolean delete(Long id);
}