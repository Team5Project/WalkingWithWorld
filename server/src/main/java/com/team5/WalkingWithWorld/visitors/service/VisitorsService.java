package com.team5.WalkingWithWorld.visitors.service;

import com.team5.WalkingWithWorld.visitors.dto.VisitorsDTO;

public interface VisitorsService {
    void deleteVisitors(VisitorsDTO visitorsDTO, Long visitorsId);

    /*void updateVisitors(VisitorsDTO visitorsDTO, Long visitorsId);*/
}
