package com.sqa.simplyrugby.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.Volunteer;
import com.sqa.simplyrugby.service.VolunteerService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    // 志愿者数据存储路径：项目根目录下的 volunteers.json
    private static final String FILE_PATH = "volunteers.json";
    // 注入 SpringBoot 自带的 Jackson 工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Volunteer> getAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Volunteer>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Volunteer getById(Long id) {
        List<Volunteer> list = getAll();
        return list.stream()
                .filter(v -> id.equals(v.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(Volunteer volunteer) {
        List<Volunteer> list = getAll();
        // 自动生成自增ID
        Long maxId = list.stream()
                .map(Volunteer::getId)
                .max(Long::compareTo)
                .orElse(0L);
        volunteer.setId(maxId + 1);
        volunteer.setCreateTime(LocalDateTime.now());
        volunteer.setUpdateTime(LocalDateTime.now());
        volunteer.setStatus("ACTIVE");
        list.add(volunteer);
        return saveAll(list);
    }

    @Override
    public boolean update(Volunteer volunteer) {
        List<Volunteer> list = getAll();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (volunteer.getId().equals(list.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        volunteer.setUpdateTime(LocalDateTime.now());
        list.set(index, volunteer);
        return saveAll(list);
    }

    @Override
    public boolean delete(Long id) {
        List<Volunteer> list = getAll();
        List<Volunteer> newList = list.stream()
                .filter(v -> !id.equals(v.getId()))
                .collect(Collectors.toList());
        if (newList.size() == list.size()) {
            return false;
        }
        return saveAll(newList);
    }

    @Override
    public boolean archive(Long id) {
        Volunteer v = getById(id);
        if (v == null) {
            return false;
        }
        v.setStatus("ARCHIVED");
        return update(v);
    }

    @Override
    public boolean restore(Long id) {
        Volunteer v = getById(id);
        if (v == null) {
            return false;
        }
        v.setStatus("ACTIVE");
        return update(v);
    }

    @Override
    public List<Volunteer> findBySkill(String skill) {
        List<Volunteer> list = getAll();
        return list.stream()
                .filter(v -> v.getSkill() != null && v.getSkill().contains(skill))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateAvailability(Volunteer v) {
        return update(v);
    }

    // 私有方法：保存所有志愿者数据到 JSON 文件
    private boolean saveAll(List<Volunteer> list) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}