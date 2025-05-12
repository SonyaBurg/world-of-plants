-- -- These are already implicitly created by unique constraints
-- -- CREATE UNIQUE INDEX idx_users_login ON users(login);
-- -- CREATE UNIQUE INDEX idx_users_email ON users(email);
--
CREATE INDEX idx_plants_category ON plants(category);

CREATE INDEX idx_orders_user_id ON orders(user_id);

CREATE INDEX idx_orders_status ON orders(status);

CREATE INDEX idx_ordered_plants_order_id ON ordered_plants(order_id);

CREATE INDEX idx_basket_items_user_id ON basket_items(user_id);