package com.sqa.simplyrugby.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.Document;
import com.sqa.simplyrugby.service.DocumentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    // 文档数据存储路径：项目根目录下的 documents.json
    private static final String FILE_PATH = "documents.json";
    // 注入 SpringBoot 自带的 Jackson 工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Document> list() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Document>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Document getById(Long id) {
        List<Document> list = list();
        return list.stream()
                .filter(doc -> id.equals(doc.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(Document document) {
        List<Document> list = list();
        // 自动生成自增ID
        Long maxId = list.stream()
                .map(Document::getId)
                .max(Long::compareTo)
                .orElse(0L);
        document.setId(maxId + 1);
        document.setUploadTime(LocalDateTime.now());
        list.add(document);
        return saveAll(list);
    }

    @Override
    public boolean update(Document document) {
        List<Document> list = list();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (document.getId().equals(list.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        list.set(index, document);
        return saveAll(list);
    }

    @Override
    public boolean delete(Long id) {
        List<Document> list = list();
        List<Document> newList = list.stream()
                .filter(doc -> !id.equals(doc.getId()))
                .collect(Collectors.toList());
        if (newList.size() == list.size()) {
            return false;
        }
        return saveAll(newList);
    }

    // 私有方法：保存所有文档数据到 JSON 文件
    private boolean saveAll(List<Document> list) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}