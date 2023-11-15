package com.team5.WalkingWithWorld.visitors.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVisitors is a Querydsl query type for Visitors
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVisitors extends EntityPathBase<Visitors> {

    private static final long serialVersionUID = 2073550463L;

    public static final QVisitors visitors = new QVisitors("visitors");

    public final com.team5.WalkingWithWorld.global.entity.QAuditingFields _super = new com.team5.WalkingWithWorld.global.entity.QAuditingFields(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public QVisitors(String variable) {
        super(Visitors.class, forVariable(variable));
    }

    public QVisitors(Path<? extends Visitors> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVisitors(PathMetadata metadata) {
        super(Visitors.class, metadata);
    }

}

