drop table if exists products cascade;

create table products
(
    id                  bigserial
        constraint products_pk
            primary key,
    name                varchar(50) not null,
    price               numeric     not null,
    product_category_id bigint      not null
        constraint products_product_category_id_fk
            references product_category
            on update cascade on delete cascade,
    info                varchar(150)
);

alter table products
    owner to postgres;

insert into products (name, price, product_category_id, info)
values ('Apple iphone 14', 999.99, 1, 'stylish ios phone'),
       ('Samsung S22', 799.99, 1, 'android  phone'),
       ('Xiaomi 13', 650, 1, 'android phone with miua'),
       ('Samsung S23', 950, 1, 'android flagman phone'),
       ('Xiaomi 13pro', 850, 1, 'global android phone with miua'),
       ('LG 55NAN', 459.99, 2, 'lg TV 55d'),
       ('LG 49S', 320, 2, 'lg TV 49d'),
       ('Sony KD-55', 540, 2, 'sony TV 55d');
