package com.sqa.simplyrugby.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> List<T> readList(Class<T> clazz, String path) {
        try {
            File file = new File(path);
            if (!file.exists()) return new ArrayList<>();

            return MAPPER.readValue(
                    file,
                    MAPPER.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> void writeList(List<T> list, String path) {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(
                    new File(path),
                    list
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}