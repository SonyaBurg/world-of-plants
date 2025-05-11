-- These are already implicitly created by unique constraints
-- CREATE UNIQUE INDEX idx_users_login ON users(login);
-- CREATE UNIQUE INDEX idx_users_email ON users(email);

-- Category is likely to be frequently filtered on
CREATE INDEX idx_plants_category ON plants(category);

-- Critical for looking up user orders
CREATE INDEX idx_orders_user_id ON orders(user_id);

-- For filtering orders by date ranges
CREATE INDEX idx_orders_date ON orders(date);

-- For filtering orders by status
CREATE INDEX idx_orders_status ON orders(status);

-- Critical for looking up plants in an order
CREATE INDEX idx_ordered_plants_order_id ON ordered_plants(order_id);

-- For searching orders by plant name
CREATE INDEX idx_ordered_plants_name ON ordered_plants(name);

-- Both are foreign keys and likely searched frequently
CREATE INDEX idx_users_to_orders_user_id ON users_to_orders(user_id);
CREATE INDEX idx_users_to_orders_order_id ON users_to_orders(order_id);

-- Critical for looking up a user's basket
CREATE INDEX idx_basket_items_user_id ON basket_items(user_id);

-- For inventory management
CREATE INDEX idx_basket_items_plant_id ON basket_items(plant_id);
