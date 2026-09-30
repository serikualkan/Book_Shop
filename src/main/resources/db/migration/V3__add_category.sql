CREATE TABLE IF NOT EXISTS categories (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL UNIQUE
    );

ALTER TABLE books
    ADD COLUMN IF NOT EXISTS category_id BIGINT;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_name = 'fk_books_category'
          AND tc.table_name = 'books'
          AND tc.constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE books
    ADD CONSTRAINT fk_books_category
        FOREIGN KEY (category_id) REFERENCES categories(id);
END IF;
END $$;
