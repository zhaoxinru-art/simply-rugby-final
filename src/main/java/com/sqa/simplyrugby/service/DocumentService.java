package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.Document;
import java.util.List;

public interface DocumentService {
    List<Document> list();
    Document getById(Long id);
    boolean add(Document document);
    boolean update(Document document);
    boolean delete(Long id);
}