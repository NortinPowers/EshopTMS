truncate test.public.product_category cascade;
select setval('test.public.product_category_id_seq', 1, false);

insert into test.public.product_category (category)
values ('phone'),
       ('tv');
