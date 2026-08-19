package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.Volunteer;
import com.sqa.simplyrugby.service.VolunteerService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Resource
    private VolunteerService volunteerService;

    @GetMapping("/list")
    public List<Volunteer> list() {
        return volunteerService.getAll();
    }

    @GetMapping("/get/{id}")
    public Volunteer getById(@PathVariable Long id) {
        return volunteerService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Volunteer volunteer) {
        return volunteerService.add(volunteer);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Volunteer volunteer) {
        return volunteerService.update(volunteer);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return volunteerService.delete(id);
    }

    @PostMapping("/archive/{id}")
    public boolean archive(@PathVariable Long id) {
        return volunteerService.archive(id);
    }

    @PostMapping("/restore/{id}")
    public boolean restore(@PathVariable Long id) {
        return volunteerService.restore(id);
    }

    @GetMapping("/skill/{skill}")
    public List<Volunteer> findBySkill(@PathVariable String skill) {
        return volunteerService.findBySkill(skill);
    }

    @PostMapping("/availability")
    public boolean updateAvailability(@RequestBody Volunteer v) {
        return volunteerService.updateAvailability(v);
    }
}