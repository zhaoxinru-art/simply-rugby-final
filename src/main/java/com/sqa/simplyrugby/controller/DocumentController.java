package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.entity.Document;
import com.sqa.simplyrugby.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Resource
    private DocumentService documentService;

    @GetMapping("/list")
    public List<Document> list() {
        return documentService.list();
    }

    @GetMapping("/get/{id}")
    public Document getById(@PathVariable Long id) {
        return documentService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Document document) {
        return documentService.add(document);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Document document) {
        return documentService.update(document);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return documentService.delete(id);
    }
}