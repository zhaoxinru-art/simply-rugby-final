package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.Role;
import com.sqa.simplyrugby.service.RoleService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public List<Role> list() {
        return roleService.list();
    }

    @GetMapping("/get/{id}")
    public Role getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Role role) {
        return roleService.add(role);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Role role) {
        return roleService.update(role);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return roleService.delete(id);
    }
}