package com.sqa.simplyrugby.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.Participation;
import com.sqa.simplyrugby.service.ParticipationService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    private static final String FILE_PATH = "participations.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Participation> list() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try {
            return objectMapper.readValue(file, new TypeReference<List<Participation>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Participation getById(Long id) {
        return list().stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(Participation participation) {
        List<Participation> list = list();
        Long maxId = list.stream()
                .map(Participation::getId)
                .max(Long::compareTo)
                .orElse(0L);
        participation.setId(maxId + 1);
        participation.setSignTime(LocalDateTime.now());
        list.add(participation);
        return saveAll(list);
    }

    @Override
    public boolean update(Participation participation) {
        List<Participation> list = list();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (participation.getId().equals(list.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;
        list.set(index, participation);
        return saveAll(list);
    }

    @Override
    public boolean delete(Long id) {
        List<Participation> list = list();
        List<Participation> newList = list.stream()
                .filter(p -> !id.equals(p.getId()))
                .collect(Collectors.toList());
        if (newList.size() == list.size()) return false;
        return saveAll(newList);
    }

    private boolean saveAll(List<Participation> list) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}