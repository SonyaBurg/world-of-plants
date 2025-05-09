create table plants
(
    id             uuid primary key,
    name           varchar(100) not null unique,
    description    TEXT         not null,
    category       varchar(100) not null,
    items_in_stock int          not null,
    price          int          not null,
    picture_link   TEXT         not null
);

create table users
(
    id           uuid primary key,
    login        varchar(255) unique not null,
    email        varchar(255) unique not null,
    first_name   varchar(255),
    last_name    varchar(255),
    pass         varchar(255)        not null,
    role         varchar(255)        not null default 'USER',
    phone_number varchar(255),
    address      varchar(255)
);

create table orders
(
    id          uuid primary key,
    user_id     uuid             not null,
    total_price double precision not null,
    address     varchar(255)     not null,
    date        timestamp        not null,
    status      varchar(255)     not null
);

create table ordered_plants
(
    id             uuid primary key,
    name           varchar(100) not null,
    quantity       int          not null,
    order_id       uuid         not null,
    FOREIGN KEY (order_id)
        REFERENCES orders (id),
    price          int          not null,
    description    TEXT         not null,
    category       varchar(100) not null,
    picture_link   TEXT         not null
);

create table users_to_orders
(
    user_id  uuid,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    order_id uuid,
    FOREIGN KEY (order_id)
        REFERENCES orders (id)
);

create table basket_items
(
    id       uuid primary key,
    quantity int  not null,
    user_id  uuid not null,
    plant_id uuid not null,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (plant_id) REFERENCES plants (id),
    unique (user_id, plant_id)
);