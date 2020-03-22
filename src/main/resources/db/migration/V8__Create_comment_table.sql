create table comment
(
    id bigint,
    parent_id bigint,
    type bigint,
    commentator int,
    gmt_create bigint,
    gmt_modified bigint,
    like_count bigint,
    content text,
    constraint comment_pk
        primary key (id)
);