package com.sqa.simplyrugby.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.simplyrugby.common.Result;
import com.sqa.simplyrugby.entity.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController {

    // 日志对象，替代e.printStackTrace()
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    // 上传目录（项目根目录下的upload文件夹）
    private static final String uploadDir = "upload/";
    // 文档数据存储路径：项目根目录下的documents.json
    private static final String DOC_JSON_PATH = "documents.json";
    // Jackson工具，纯文件存储核心
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 文件上传接口
     * @param file 上传的文件
     * @param volunteerId 上传的志愿者ID
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("volunteerId") Long volunteerId
    ) {
        // 1. 校验文件（增强非空判断）
        if (file == null || file.isEmpty()) {
            return Result.fail("文件不能为空");
        }

        // 2. 校验文件名，避免空指针
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return Result.fail("文件名不能为空");
        }

        // 3. 创建上传目录（修复拼写，增强判断）
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                log.error("创建上传目录失败，路径：{}", uploadDir);
                return Result.fail("创建上传目录失败");
            }
        }

        // 4. 生成唯一文件名（防护空指针）
        int lastDotIndex = originalFilename.lastIndexOf(".");
        String suffix = (lastDotIndex != -1) ? originalFilename.substring(lastDotIndex) : "";
        String newFileName = UUID.randomUUID() + suffix;

        // 5. 保存文件到本地
        File dest = new File(uploadDir + newFileName);
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            log.error("文件保存失败，文件名：{}", originalFilename, e);
            return Result.fail("文件保存失败");
        }

        // 6. 保存文件信息到JSON文件（纯文件存储，替代数据库）
        // 6.1 读取现有文档数据
        List<Document> docList = readDocListFromJson();
        // 6.2 自动生成自增ID
        Long maxId = docList.stream()
                .map(Document::getId)
                .max(Long::compareTo)
                .orElse(0L);
        // 6.3 构建文档对象
        Document doc = new Document();
        doc.setId(maxId + 1);
        doc.setFileName(originalFilename);
        doc.setFilePath(uploadDir + newFileName);
        doc.setVolunteerId(volunteerId);
        doc.setFileType(suffix);
        doc.setFileSize(file.getSize());
        doc.setUploadTime(LocalDateTime.now());
        doc.setExpiryDate(LocalDateTime.now().plusMonths(6)); // 6个月后过期
        doc.setRemark("文件上传");
        // 6.4 写入JSON文件
        docList.add(doc);
        boolean saveSuccess = saveDocListToJson(docList);
        if (saveSuccess) {
            return Result.success("文件上传成功，路径：" + uploadDir + newFileName);
        } else {
            return Result.fail("文件信息保存失败");
        }
    }

    /**
     * 从JSON文件读取文档列表
     */
    private List<Document> readDocListFromJson() {
        File jsonFile = new File(DOC_JSON_PATH);
        if (!jsonFile.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<List<Document>>() {});
        } catch (Exception e) {
            log.error("读取文档JSON文件失败", e);
            return new ArrayList<>();
        }
    }

    /**
     * 保存文档列表到JSON文件
     */
    private boolean saveDocListToJson(List<Document> docList) {
        try {
            objectMapper.writeValue(new File(DOC_JSON_PATH), docList);
            return true;
        } catch (Exception e) {
            log.error("保存文档JSON文件失败", e);
            return false;
        }
    }

    /**
     * 获取所有文档列表接口
     */
    @GetMapping("/list")
    public Result<List<Document>> list() {
        List<Document> docList = readDocListFromJson();
        return Result.success(docList);
    }

    /**
     * 删除文档接口
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        List<Document> docList = readDocListFromJson();
        List<Document> newList = docList.stream()
                .filter(doc -> !id.equals(doc.getId()))
                .collect(Collectors.toList());
        if (newList.size() == docList.size()) {
            return Result.fail("文档不存在");
        }
        boolean saveSuccess = saveDocListToJson(newList);
        if (saveSuccess) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }
}