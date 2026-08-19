package com.sqa.simplyrugby.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.InternationalVolunteer;
import com.sqa.simplyrugby.service.InternationalVolunteerService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternationalVolunteerServiceImpl implements InternationalVolunteerService {

    // 国际志愿者数据存储路径：项目根目录下的 international_volunteers.json
    private static final String FILE_PATH = "international_volunteers.json";
    // 注入 SpringBoot 自带的 Jackson 工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<InternationalVolunteer> list() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<InternationalVolunteer>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public InternationalVolunteer getById(Long id) {
        return list().stream()
                .filter(v -> id.equals(v.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(InternationalVolunteer volunteer) {
        List<InternationalVolunteer> list = list();
        // 自动生成自增ID
        Long maxId = list.stream()
                .map(InternationalVolunteer::getId)
                .max(Long::compareTo)
                .orElse(0L);
        volunteer.setId(maxId + 1);
        volunteer.setCreateTime(LocalDateTime.now());
        volunteer.setStatus("ACTIVE");
        list.add(volunteer);
        return saveAll(list);
    }

    @Override
    public boolean update(InternationalVolunteer volunteer) {
        List<InternationalVolunteer> list = list();
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
        list.set(index, volunteer);
        return saveAll(list);
    }

    @Override
    public boolean delete(Long id) {
        List<InternationalVolunteer> list = list();
        List<InternationalVolunteer> newList = list.stream()
                .filter(v -> !id.equals(v.getId()))
                .collect(Collectors.toList());
        if (newList.size() == list.size()) {
            return false;
        }
        return saveAll(newList);
    }

    // 私有方法：保存所有国际志愿者数据到 JSON 文件
    private boolean saveAll(List<InternationalVolunteer> list) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}