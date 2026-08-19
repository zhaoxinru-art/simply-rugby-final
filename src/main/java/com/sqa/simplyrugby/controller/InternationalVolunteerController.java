package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.InternationalVolunteer;
import com.sqa.simplyrugby.service.InternationalVolunteerService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/international")
public class InternationalVolunteerController {

    @Resource
    private InternationalVolunteerService internationalVolunteerService;

    @GetMapping("/list")
    public List<InternationalVolunteer> list() {
        return internationalVolunteerService.list();
    }

    @GetMapping("/get/{id}")
    public InternationalVolunteer getById(@PathVariable Long id) {
        return internationalVolunteerService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody InternationalVolunteer volunteer) {
        return internationalVolunteerService.add(volunteer);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody InternationalVolunteer volunteer) {
        return internationalVolunteerService.update(volunteer);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return internationalVolunteerService.delete(id);
    }
}