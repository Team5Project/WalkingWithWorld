package com.team5.WalkingWithWorld.walkingPaths.repository.querydsl.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.team5.WalkingWithWorld.global.entity.QMap;
import com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.querydsl.WalkingPathsRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

import static com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths.walkingPaths;

public class WalkingPathsRepositoryCustomImpl extends QuerydslRepositorySupport implements WalkingPathsRepositoryCustom {
    public WalkingPathsRepositoryCustomImpl(){
        super(WalkingPaths.class);
    }

    @Override
    public Page<WalkingPaths> filterWalkingPaths(String keyword, List<String> location, int minTime, int maxTime, String minDistance, String maxDistance, Pageable pageable){
        QWalkingPaths walkingPath = QWalkingPaths.walkingPaths;
        QMap map = QMap.map;
        JPQLQuery<WalkingPaths> query =
//        List<Map> walkingPaths =
                from(map)
                        .select(walkingPath)
                        .innerJoin(map.walkingPaths, walkingPath)
                        .where(eqTitle(keyword),
                                eqAddr(location),
                                (map.time.between(minTime,maxTime).and(map.distance.between(minDistance,maxDistance)))
                        )
                        .groupBy(walkingPath)
                        .orderBy(walkingPath.createdAt.desc());


        List<WalkingPaths> walkingPaths = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        Long count =
                from(map)
                        .select(walkingPath.count())
                        .innerJoin(map.walkingPaths, walkingPath)
                        .where(eqTitle(keyword),
                                eqAddr(location),
                                (map.time.between(minTime,maxTime).and(map.distance.between(minDistance,maxDistance)))
                        )
//                        .distinct()
                        .fetchOne();
        return new PageImpl<>(walkingPaths, pageable, count);
    }


    private BooleanExpression eqTitle(String keyword){
        return keyword == null ? null : walkingPaths.title.contains(keyword);
    }

    private BooleanExpression eqAddr(List<String> location){
        if(location == null)
            return null;

        BooleanExpression result = null;

        for (String loc : location) {
            BooleanExpression condition = walkingPaths.addr.contains(loc);
            result = (result == null) ? condition : result.or(condition);
        }

        return result;
    }
}
