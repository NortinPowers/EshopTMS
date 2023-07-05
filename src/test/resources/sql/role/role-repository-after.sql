truncate test.public.roles cascade;
select setval('test.public.roles_id_seq', 1, false);
