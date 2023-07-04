truncate test.public.product_category cascade;
select setval('test.public.product_category_id_seq', 1, false);
truncate test.public.products cascade;
select setval('test.public.products_id_seq', 1, false);
truncate test.public.users cascade;
select setval('test.public.users_id_seq', 1, false);
truncate test.public.carts cascade;
select setval('test.public.carts_id_seq', 1, false);
