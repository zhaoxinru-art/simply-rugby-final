package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.Assignment;
import java.util.List;

public interface AssignmentService {
    List<Assignment> list();
    Assignment getById(Long id);
    boolean add(Assignment assignment);
    boolean update(Assignment assignment);
    boolean delete(Long id);
}