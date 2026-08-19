package com.sqa.simplyrugby.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.Assignment;
import com.sqa.simplyrugby.service.AssignmentService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private static final String FILE_PATH = "assignments.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Assignment> list() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try {
            return objectMapper.readValue(file, new TypeReference<List<Assignment>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Assignment getById(Long id) {
        return list().stream()
                .filter(a -> id.equals(a.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(Assignment assignment) {
        List<Assignment> list = list();
        Long maxId = list.stream()
                .map(Assignment::getId)
                .max(Long::compareTo)
                .orElse(0L);
        assignment.setId(maxId + 1);
        assignment.setAssignTime(LocalDateTime.now());
        assignment.setStatus("ASSIGNED");
        list.add(assignment);
        return saveAll(list);
    }

    @Override
    public boolean update(Assignment assignment) {
        List<Assignment> list = list();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (assignment.getId().equals(list.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;
        list.set(index, assignment);
        return saveAll(list);
    }

    @Override
    public boolean delete(Long id) {
        List<Assignment> list = list();
        List<Assignment> newList = list.stream()
                .filter(a -> !id.equals(a.getId()))
                .collect(Collectors.toList());
        if (newList.size() == list.size()) return false;
        return saveAll(newList);
    }

    private boolean saveAll(List<Assignment> list) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}