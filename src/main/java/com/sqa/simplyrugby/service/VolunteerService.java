package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.Volunteer;
import java.util.List;

public interface VolunteerService {
    List<Volunteer> getAll();
    Volunteer getById(Long id);
    boolean add(Volunteer volunteer);
    boolean update(Volunteer volunteer);
    boolean delete(Long id);
    boolean archive(Long id);
    boolean restore(Long id);
    List<Volunteer> findBySkill(String skill);
    boolean updateAvailability(Volunteer v);
}