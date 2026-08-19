package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.Event;
import com.sqa.simplyrugby.service.EventService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Resource
    private EventService eventService;

    @GetMapping("/list")
    public List<Event> list() {
        return eventService.list();
    }

    @GetMapping("/get/{id}")
    public Event getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Event event) {
        return eventService.add(event);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Event event) {
        return eventService.update(event);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return eventService.delete(id);
    }
}