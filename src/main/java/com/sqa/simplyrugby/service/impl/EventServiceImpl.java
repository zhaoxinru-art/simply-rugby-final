package com.sqa.simplyrugby.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.Event;
import com.sqa.simplyrugby.service.EventService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final String FILE_PATH = "events.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Event> list() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try {
            return objectMapper.readValue(file, new TypeReference<List<Event>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Event getById(Long id) {
        return list().stream()
                .filter(e -> id.equals(e.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(Event event) {
        List<Event> list = list();
        Long maxId = list.stream()
                .map(Event::getId)
                .max(Long::compareTo)
                .orElse(0L);
        event.setId(maxId + 1);
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        event.setStatus("UPCOMING");
        list.add(event);
        return saveAll(list);
    }

    @Override
    public boolean update(Event event) {
        List<Event> list = list();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (event.getId().equals(list.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;
        event.setUpdateTime(LocalDateTime.now());
        list.set(index, event);
        return saveAll(list);
    }

    @Override
    public boolean delete(Long id) {
        List<Event> list = list();
        List<Event> newList = list.stream()
                .filter(e -> !id.equals(e.getId()))
                .collect(Collectors.toList());
        if (newList.size() == list.size()) return false;
        return saveAll(newList);
    }

    private boolean saveAll(List<Event> list) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}