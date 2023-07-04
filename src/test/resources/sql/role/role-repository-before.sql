truncate test.public.roles cascade;
select setval('test.public.roles_id_seq', 1, false);

insert into test.public.roles (role)
values ('ROLE_ADMIN'),
       ('ROLE_USER');
