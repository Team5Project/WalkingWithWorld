package com.team5.WalkingWithWorld.walkingPaths.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkingPaths is a Querydsl query type for WalkingPaths
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkingPaths extends EntityPathBase<WalkingPaths> {

    private static final long serialVersionUID = 1864849375L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkingPaths walkingPaths = new QWalkingPaths("walkingPaths");

    public final com.team5.WalkingWithWorld.global.entity.QAuditingFields _super = new com.team5.WalkingWithWorld.global.entity.QAuditingFields(this);

    public final StringPath addr = createString("addr");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.team5.WalkingWithWorld.global.entity.Map, com.team5.WalkingWithWorld.global.entity.QMap> mapList = this.<com.team5.WalkingWithWorld.global.entity.Map, com.team5.WalkingWithWorld.global.entity.QMap>createList("mapList", com.team5.WalkingWithWorld.global.entity.Map.class, com.team5.WalkingWithWorld.global.entity.QMap.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath title = createString("title");

    public final com.team5.WalkingWithWorld.users.entity.QUsers users;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QWalkingPaths(String variable) {
        this(WalkingPaths.class, forVariable(variable), INITS);
    }

    public QWalkingPaths(Path<? extends WalkingPaths> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkingPaths(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkingPaths(PathMetadata metadata, PathInits inits) {
        this(WalkingPaths.class, metadata, inits);
    }

    public QWalkingPaths(Class<? extends WalkingPaths> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.team5.WalkingWithWorld.users.entity.QUsers(forProperty("users")) : null;
    }

}

