INSERT INTO categorias (id, nombre, orden)
VALUES (1, 'Bebidas calientes', 1),
       (2, 'Bebidas frías', 2),
       (3, 'Sándwiches', 3),
       (4, 'Repostería', 4);

INSERT INTO opciones_personalizacion (id, tipo, nombre, precio_extra, predeterminada, activo)
VALUES (1, 'MILK', 'Leche entera', 0.00, TRUE, TRUE),
       (2, 'MILK', 'Leche sin lactosa', 0.00, FALSE, TRUE),
       (3, 'MILK', 'Leche de soja', 0.00, FALSE, TRUE),
       (4, 'MILK', 'Leche descremada', 0.00, FALSE, TRUE),
       (5, 'MILK', 'Leche de almendras', 0.70, FALSE, TRUE),
       (6, 'MILK', 'Leche de avena', 0.70, FALSE, TRUE),
       (7, 'CREAM', 'Sin crema batida', 0.00, TRUE, TRUE),
       (8, 'CREAM', 'Con crema batida', 0.50, FALSE, TRUE),
       (9, 'CAFFEINE', 'Con cafeína', 0.00, TRUE, TRUE),
       (10, 'CAFFEINE', 'Sin cafeína', 0.00, FALSE, TRUE);

SELECT setval(pg_get_serial_sequence('categorias', 'id'), (SELECT MAX(id) FROM categorias));
SELECT setval(pg_get_serial_sequence('opciones_personalizacion', 'id'), (SELECT MAX(id) FROM opciones_personalizacion));
