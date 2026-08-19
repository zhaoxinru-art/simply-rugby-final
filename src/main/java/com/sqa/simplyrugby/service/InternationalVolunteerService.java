package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.entity.InternationalVolunteer;
import java.util.List;

public interface InternationalVolunteerService {
    List<InternationalVolunteer> list();
    InternationalVolunteer getById(Long id);
    boolean add(InternationalVolunteer volunteer);
    boolean update(InternationalVolunteer volunteer);
    boolean delete(Long id);
}