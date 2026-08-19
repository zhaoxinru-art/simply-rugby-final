package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.Participation;
import java.util.List;

public interface ParticipationService {
    List<Participation> list();
    Participation getById(Long id);
    boolean add(Participation participation);
    boolean update(Participation participation);
    boolean delete(Long id);
}