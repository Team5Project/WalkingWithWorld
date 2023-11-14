package com.team5.WalkingWithWorld.reviews.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviews is a Querydsl query type for Reviews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviews extends EntityPathBase<Reviews> {

    private static final long serialVersionUID = -510676421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviews reviews = new QReviews("reviews");

    public final com.team5.WalkingWithWorld.global.entity.QAuditingFields _super = new com.team5.WalkingWithWorld.global.entity.QAuditingFields(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final com.team5.WalkingWithWorld.users.entity.QUsers users;

    public final com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths walkingPaths;

    public QReviews(String variable) {
        this(Reviews.class, forVariable(variable), INITS);
    }

    public QReviews(Path<? extends Reviews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviews(PathMetadata metadata, PathInits inits) {
        this(Reviews.class, metadata, inits);
    }

    public QReviews(Class<? extends Reviews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.team5.WalkingWithWorld.users.entity.QUsers(forProperty("users")) : null;
        this.walkingPaths = inits.isInitialized("walkingPaths") ? new com.team5.WalkingWithWorld.walkingPaths.entity.QWalkingPaths(forProperty("walkingPaths"), inits.get("walkingPaths")) : null;
    }

}

