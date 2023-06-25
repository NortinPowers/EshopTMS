drop table if exists carts cascade;

create table public.carts
(
    id         bigserial
        constraint carts_pk
            primary key,
    user_id    bigint            not null
        constraint carts_users_id_fk
            references public.users
            on update cascade on delete cascade,
    product_id bigint            not null
        constraint carts_products_id_fk
            references public.products
            on update cascade on delete cascade,
    cart       boolean,
    favorite   boolean,
    count      integer default 1 not null
);

alter table public.carts
    owner to postgres;
