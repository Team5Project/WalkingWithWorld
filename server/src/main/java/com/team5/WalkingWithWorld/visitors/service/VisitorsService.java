package com.team5.WalkingWithWorld.visitors.service;

import com.team5.WalkingWithWorld.visitors.dto.VisitorsDTO;
import com.team5.WalkingWithWorld.visitors.dto.VisitorsUpdateRequestDTO;

public interface VisitorsService {
    void deleteVisitors(Long id, String password);

    public void updateVisitors(Long id, VisitorsUpdateRequestDTO requestDTO);
}
