truncate test.public.product_category cascade;
select setval('test.public.product_category_id_seq', 1, false);
truncate test.public.products cascade;
select setval('test.public.products_id_seq', 1, false);
truncate test.public.roles cascade;
select setval('test.public.roles_id_seq', 1, false);
truncate test.public.users cascade;
select setval('test.public.users_id_seq', 1, false);
truncate test.public.carts cascade;
select setval('test.public.carts_id_seq', 1, false);


insert into test.public.product_category (category)
values ('phone'),
       ('tv');

insert into test.public.products (name, price, product_category_id, info)
values ('Apple iphone 14', 999.99, 1, 'stylish ios phone'),
       ('Samsung S22', 799.99, 1, 'android  phone'),
       ('LG 55NAN', 459.99, 2, 'lg TV 55d');

insert into test.public.roles (role)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into test.public.users (login, password, name, surname, email, birthday, role_id)
values ('login', 'password', 'name', 'surname', 'test@email.com', '2000-10-10', 1);

insert into test.public.carts (user_id, product_id, cart, favorite, count)
values (1, 1, true, false, 2),
       (1, 2, false, true, 1),
       (1, 3, false, true, 1);
