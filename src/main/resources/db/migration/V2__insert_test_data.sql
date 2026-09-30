-- 1) Permissions (без UNIQUE тоже работает)
INSERT INTO t_permission (name)
SELECT 'USER'
WHERE NOT EXISTS (SELECT 1 FROM t_permission WHERE name = 'USER');

INSERT INTO t_permission (name)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM t_permission WHERE name = 'ADMIN');

-- 2) Users
INSERT INTO users (username, email, password)
SELECT 'test', 'test@test.com', '$2a$10$dummyhashedpassword1'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'test@test.com');

INSERT INTO users (username, email, password)
SELECT 'admin', 'admin@test.com', '$2a$10$dummyhashedpassword2'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@test.com');

-- 3) user_permissions (test -> USER)
INSERT INTO user_permissions (user_id, permission_id)
SELECT u.id, p.id
FROM users u
         JOIN t_permission p ON p.name = 'USER'
WHERE u.email = 'test@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM user_permissions up
    WHERE up.user_id = u.id AND up.permission_id = p.id
);

-- admin -> ADMIN
INSERT INTO user_permissions (user_id, permission_id)
SELECT u.id, p.id
FROM users u
         JOIN t_permission p ON p.name = 'ADMIN'
WHERE u.email = 'admin@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM user_permissions up
    WHERE up.user_id = u.id AND up.permission_id = p.id
);

-- 4) Books (по title проверяем)
INSERT INTO books (title, name, author, genre, year, price, image_url)
SELECT 'Clean Code', 'Clean Code', 'Robert C. Martin', 'Programming', 2008, 5500, 'https://example.com/clean-code.jpg'
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Clean Code');

INSERT INTO books (title, name, author, genre, year, price, image_url)
SELECT 'Effective Java', 'Effective Java', 'Joshua Bloch', 'Programming', 2018, 6200, 'https://example.com/effective-java.jpg'
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Effective Java');

INSERT INTO books (title, name, author, genre, year, price, image_url)
SELECT 'Spring in Action', 'Spring in Action', 'Craig Walls', 'Programming', 2021, 7000, 'https://example.com/spring-in-action.jpg'
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Spring in Action');

INSERT INTO books (title, name, author, genre, year, price, image_url)
SELECT '1984', '1984', 'George Orwell', 'Fiction', 1949, 3000, 'https://example.com/1984.jpg'
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = '1984');

-- 5) Cart для test
INSERT INTO cart (user_id)
SELECT u.id
FROM users u
WHERE u.email = 'test@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM cart c WHERE c.user_id = u.id
);

-- 6) cart_item (Clean Code x1, 1984 x2)
INSERT INTO cart_item (cart_id, book_id, quantity)
SELECT c.id, b.id, 1
FROM cart c
         JOIN users u ON u.id = c.user_id AND u.email = 'test@test.com'
         JOIN books b ON b.title = 'Clean Code'
WHERE NOT EXISTS (
    SELECT 1 FROM cart_item ci WHERE ci.cart_id = c.id AND ci.book_id = b.id
);

INSERT INTO cart_item (cart_id, book_id, quantity)
SELECT c.id, b.id, 2
FROM cart c
         JOIN users u ON u.id = c.user_id AND u.email = 'test@test.com'
         JOIN books b ON b.title = '1984'
WHERE NOT EXISTS (
    SELECT 1 FROM cart_item ci WHERE ci.cart_id = c.id AND ci.book_id = b.id
);

-- 7) Order для test (создаём только если ещё нет заказа со статусом NEW)
INSERT INTO orders (user_id, total_price, status, created_at)
SELECT u.id, 11500, 'NEW', NOW()
FROM users u
WHERE u.email = 'test@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.user_id = u.id AND o.status = 'NEW'
);

-- Берём последний NEW заказ
WITH last_order AS (
    SELECT o.id
    FROM orders o
             JOIN users u ON u.id = o.user_id
    WHERE u.email = 'test@test.com' AND o.status = 'NEW'
    ORDER BY o.created_at DESC
    LIMIT 1
)
INSERT INTO order_item (order_id, book_id, quantity, price)
SELECT lo.id, b.id, 1, 5500
FROM last_order lo
         JOIN books b ON b.title = 'Clean Code'
WHERE NOT EXISTS (
    SELECT 1 FROM order_item oi WHERE oi.order_id = lo.id AND oi.book_id = b.id
);

WITH last_order AS (
    SELECT o.id
    FROM orders o
             JOIN users u ON u.id = o.user_id
    WHERE u.email = 'test@test.com' AND o.status = 'NEW'
    ORDER BY o.created_at DESC
    LIMIT 1
)
INSERT INTO order_item (order_id, book_id, quantity, price)
SELECT lo.id, b.id, 2, 3000
FROM last_order lo
         JOIN books b ON b.title = '1984'
WHERE NOT EXISTS (
    SELECT 1 FROM order_item oi WHERE oi.order_id = lo.id AND oi.book_id = b.id
);

WITH last_order AS (
    SELECT o.id
    FROM orders o
             JOIN users u ON u.id = o.user_id
    WHERE u.email = 'test@test.com' AND o.status = 'NEW'
    ORDER BY o.created_at DESC
    LIMIT 1
)
INSERT INTO payment (order_id, amount, status, payment_date)
SELECT lo.id, 11500, 'PAID', NOW()
FROM last_order lo
WHERE NOT EXISTS (
    SELECT 1 FROM payment p WHERE p.order_id = lo.id
);
