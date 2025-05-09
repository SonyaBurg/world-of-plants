alter table orders
    add column order_number SERIAL not null;

create sequence order_number_seq restart with 100;

with ordered_rows as (
    select id, ROW_NUMBER() over (order by date) as row_num
    from orders
)
update orders
set order_number = ordered_rows.row_num
    from ordered_rows
where orders.id = ordered_rows.id;