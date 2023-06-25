truncate table users cascade;

create table roles
(
    id   bigserial
        constraint roles_pk
            primary key,
    role varchar(20) not null
);

insert into roles (role)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

alter table users
    add role_id bigint not null;

alter table users
    add constraint users_roles_id_fk
        foreign key (role_id) references roles
            on update cascade on delete cascade;
