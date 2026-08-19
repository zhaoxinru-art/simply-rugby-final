package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.Event;
import java.util.List;

public interface EventService {
    List<Event> list();
    Event getById(Long id);
    boolean add(Event event);
    boolean update(Event event);
    boolean delete(Long id);
}