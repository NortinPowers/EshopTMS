truncate test.public.product_category cascade;
select setval('test.public.product_category_id_seq', 1, false);
truncate test.public.products cascade;
select setval('test.public.products_id_seq', 1, false);

insert into test.public.product_category (category)
values ('phone'),
       ('tv');

insert into test.public.products (name, price, product_category_id, info)
values ('Apple iphone 14', 999.99, 1, 'stylish ios phone'),
       ('Samsung S22', 799.99, 1, 'android  phone'),
       ('LG 55NAN', 459.99, 2, 'lg TV 55d');
