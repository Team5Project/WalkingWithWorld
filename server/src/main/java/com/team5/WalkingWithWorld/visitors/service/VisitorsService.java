package com.team5.WalkingWithWorld.visitors.service;

import com.team5.WalkingWithWorld.visitors.entity.Visitors;

public interface VisitorsService {
    void deleteVisitors(Long id, String password);

    Visitors updateVisitors (Long id, String password, String content);

    /*void updateVisitors(VisitorsDTO visitorsDTO, Long visitorsId);*/
}
