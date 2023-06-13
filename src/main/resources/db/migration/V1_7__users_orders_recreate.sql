drop table if exists order_product cascade;

create table order_product
(
    order_id   bigint not null
        constraint order_product_orders_id_fk
            references orders
            on update cascade on delete cascade,
    product_id bigint not null
        constraint order_product_products_id_fk
            references products
            on update cascade on delete cascade,
    id         bigserial
        constraint order_product_pk
            primary key
);