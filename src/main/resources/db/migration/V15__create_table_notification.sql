create table notification
(
    id bigint auto_increment,
    notifier bigint,
    receiver bigint,
    outerId bigint,
    type int,
    gmt_create bigint,
    status int default 0,
    constraint notification_pk
        primary key (id)
);