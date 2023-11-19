package com.team5.WalkingWithWorld.visitors.service.impl;

import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.visitors.dto.VisitorsDTO;
import com.team5.WalkingWithWorld.visitors.entity.Visitors;
import com.team5.WalkingWithWorld.visitors.repository.VisitorsRepository;
import com.team5.WalkingWithWorld.visitors.service.VisitorsService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VisitorsServiceImpl implements VisitorsService {

    private final VisitorsRepository visitorsRepository;
    public VisitorsServiceImpl(VisitorsRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
    }

    @Override
    @Transactional
    public void deleteVisitors(Long id, String password){
        /*System.out.println(visitorsDTO);*/
        Visitors visitors =visitorsRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.VISITORS_ERROR_FOUND));
        if(Objects.equals(password, visitors.getPassword())){
            visitorsRepository.deleteByIdAndPassword(id, password);
        } else {
            throw new RuntimeException("패스워드가 다릅니다: "+ password);
        }
    }

   /* @Override
    @Transactional
    public void updateVisitors(VisitorsDTO visitorsDTO, Long visitorsId){
        Visitors visitors = visitorsRepository.findById(visitorsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode. VISITORS_ERROR_FOUND));
        if(Objects.equals(visitors.getPassword(), visitorsDTO.getPassword())){
            visitorsRepository.updateByIdAndPassword(visitorsId, visitorsDTO.getPassword());
        }else {
            throw new RuntimeException("패스워드가 다릅니다");
        }
    }*/


}