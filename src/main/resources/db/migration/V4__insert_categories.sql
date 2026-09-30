INSERT INTO categories(name) VALUES
                                 ('Programming'),
                                 ('Fiction'),
                                 ('Science'),
                                 ('History')
    ON CONFLICT (name) DO NOTHING;
