package com.team5.WalkingWithWorld.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotos is a Querydsl query type for Photos
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotos extends EntityPathBase<Photos> {

    private static final long serialVersionUID = -2132744423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotos photos = new QPhotos("photos");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgPath = createString("imgPath");

    public final com.team5.WalkingWithWorld.reviews.entity.QReviews reviews;

    public final com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths walkingPaths;

    public QPhotos(String variable) {
        this(Photos.class, forVariable(variable), INITS);
    }

    public QPhotos(Path<? extends Photos> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotos(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotos(PathMetadata metadata, PathInits inits) {
        this(Photos.class, metadata, inits);
    }

    public QPhotos(Class<? extends Photos> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reviews = inits.isInitialized("reviews") ? new com.team5.WalkingWithWorld.reviews.entity.QReviews(forProperty("reviews"), inits.get("reviews")) : null;
        this.walkingPaths = inits.isInitialized("walkingPaths") ? new com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths(forProperty("walkingPaths"), inits.get("walkingPaths")) : null;
    }

}

