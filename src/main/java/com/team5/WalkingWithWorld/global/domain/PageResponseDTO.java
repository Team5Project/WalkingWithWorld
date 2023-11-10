package com.team5.WalkingWithWorld.global.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> {
    private List<T> data;
    private PageInfo pageInfo;
    private List<Integer> barNumber;

    public PageResponseDTO(List<T> data, Page page, List<Integer> barNumber) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(), page.getTotalElements(), page.getTotalPages());
        this.barNumber = barNumber;
    }

    public PageResponseDTO(List<T> data, Page page){
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
