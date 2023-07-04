truncate test.public.product_category cascade;
select setval('test.public.product_category_id_seq', 1, false);
truncate test.public.products cascade;
select setval('test.public.products_id_seq', 1, false);
truncate test.public.users cascade;
select setval('test.public.users_id_seq', 1, false);
truncate test.public.orders cascade;
select setval('test.public.orders_id_seq', 1, false);
truncate test.public.order_product cascade;
select setval('test.public.order_product_id_seq', 1, false);

insert into product_category (category)
values ('phone'),
       ('tv');

insert into products (name, price, product_category_id, info)
values ('Apple iphone 14', 999.99, 1, 'stylish ios phone'),
       ('Samsung S22', 799.99, 1, 'android  phone'),
       ('LG 55NAN', 459.99, 2, 'lg TV 55d');

insert into test.public.roles (role)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into test.public.users (login, password, name, surname, email, birthday, role_id)
values ('login', 'password', 'name', 'surname', 'test@email.com', '2000-10-10', 1);

insert into test.public.orders (name, date, user_id)
values ('#1', '2023-04-07', 1);

insert into test.public.order_product (order_id, product_id)
values (1, 1),
       (1, 1),
       (1, 3);
