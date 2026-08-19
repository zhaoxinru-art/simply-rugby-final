package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.Participation;
import com.sqa.simplyrugby.service.ParticipationService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    @Resource
    private ParticipationService participationService;

    @GetMapping("/list")
    public List<Participation> list() {
        return participationService.list();
    }

    @GetMapping("/get/{id}")
    public Participation getById(@PathVariable Long id) {
        return participationService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Participation participation) {
        return participationService.add(participation);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Participation participation) {
        return participationService.update(participation);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return participationService.delete(id);
    }
}