package com.sqa.simplyrugby.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static final String USER_JSON_PATH = "users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 从JSON文件读取用户列表
     */
    public List<User> getUsers() {
        File jsonFile = new File(USER_JSON_PATH);
        if (!jsonFile.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            log.error("读取用户JSON文件失败", e);
            return new ArrayList<>();
        }
    }

    /**
     * 保存用户列表到JSON文件
     */
    public boolean saveUsers(List<User> userList) {
        try {
            objectMapper.writeValue(new File(USER_JSON_PATH), userList);
            return true;
        } catch (Exception e) {
            log.error("保存用户JSON文件失败", e);
            return false;
        }
    }
}