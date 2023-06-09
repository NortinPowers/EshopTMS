drop table if exists orders cascade;

create table orders
(
    id      bigserial
        constraint orders_pk
            primary key,
    name    varchar(50) not null,
    date    date        not null,
    user_id integer     not null
        constraint orders_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table orders
    owner to postgres;
