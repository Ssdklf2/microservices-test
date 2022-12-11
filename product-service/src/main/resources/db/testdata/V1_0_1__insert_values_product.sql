INSERT INTO product (product_id, title, description, price)
VALUES ('57c42ea4-2c08-408f-9d65-878ea4f39536', 'bike', 'desc1', 3000),
       ('57c42ea4-2c08-408f-9d65-878ea4f39535', 'TV', 'desc2', 10000),
       ('57c42ea4-2c08-408f-9d65-878ea4f39534', 'couch', 'desc3', 6000),
       ('57c42ea4-2c08-408f-9d65-878ea4f39533', 'chair', 'desc4', 500)
ON CONFLICT DO NOTHING;