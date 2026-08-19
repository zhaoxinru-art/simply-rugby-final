package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.Assignment;
import com.sqa.simplyrugby.service.AssignmentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Resource
    private AssignmentService assignmentService;

    @GetMapping("/list")
    public List<Assignment> list() {
        return assignmentService.list();
    }

    @GetMapping("/get/{id}")
    public Assignment getById(@PathVariable Long id) {
        return assignmentService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Assignment assignment) {
        return assignmentService.add(assignment);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Assignment assignment) {
        return assignmentService.update(assignment);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return assignmentService.delete(id);
    }
}