truncate test.public.roles cascade;
select setval('test.public.roles_id_seq', 1, false);
truncate test.public.users cascade;
select setval('test.public.users_id_seq', 1, false);

insert into test.public.roles (role)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into test.public.users (login, password, name, surname, email, birthday, role_id)
values ('login', 'password', 'name', 'surname', 'test@email.com', '2000-10-10', 1);
