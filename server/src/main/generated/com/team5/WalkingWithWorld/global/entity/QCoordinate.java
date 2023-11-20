package com.team5.WalkingWithWorld.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoordinate is a Querydsl query type for Coordinate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoordinate extends EntityPathBase<Coordinate> {

    private static final long serialVersionUID = -223426544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoordinate coordinate = new QCoordinate("coordinate");

    public final StringPath coordinateX = createString("coordinateX");

    public final StringPath coordinateY = createString("coordinateY");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths walkingPaths;

    public QCoordinate(String variable) {
        this(Coordinate.class, forVariable(variable), INITS);
    }

    public QCoordinate(Path<? extends Coordinate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoordinate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoordinate(PathMetadata metadata, PathInits inits) {
        this(Coordinate.class, metadata, inits);
    }

    public QCoordinate(Class<? extends Coordinate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.walkingPaths = inits.isInitialized("walkingPaths") ? new com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths(forProperty("walkingPaths"), inits.get("walkingPaths")) : null;
    }

}

