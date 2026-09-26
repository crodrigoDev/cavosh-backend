-- Precios de ejemplo. Las imágenes se cargarán cuando estén disponibles sus URLs.
INSERT INTO productos (id, nombre, descripcion, imagen_url, nuevo, frecuente, disponible, personalizable, categoria_id)
VALUES (1, 'Caramel Macchiato', 'Espresso con leche y un toque de caramelo.', NULL, TRUE, TRUE, TRUE, TRUE, 1),
       (2, 'Vanilla Latte', 'Espresso con leche y un suave aroma de vainilla.', NULL, TRUE, FALSE, TRUE, TRUE, 1),
       (3, 'Caffe Mocha', 'Espresso con leche y chocolate.', NULL, TRUE, TRUE, TRUE, TRUE, 1),
       (4, 'Traditional Cappuccino', 'Espresso con leche y una capa de espuma.', NULL, FALSE, TRUE, TRUE, TRUE, 1),
       (5, 'Cinnamon Roll', 'Roll de canela con un suave glaseado.', NULL, TRUE, TRUE, TRUE, FALSE, 4),
       (6, 'Croissant', 'Croissant de mantequilla de masa hojaldrada.', NULL, FALSE, FALSE, TRUE, FALSE, 4),
       (7, 'Iced Latte', 'Espresso con leche y hielo.', NULL, TRUE, FALSE, TRUE, TRUE, 2),
       (8, 'Sándwich de pollo', 'Sándwich de pollo con lechuga y tomate.', NULL, FALSE, FALSE, TRUE, FALSE, 3);

INSERT INTO producto_sizes (id, nombre, precio, predeterminado, producto_id)
VALUES (1, 'SMALL', 4.00, TRUE, 1),
       (2, 'MEDIUM', 5.00, FALSE, 1),
       (3, 'LARGE', 6.00, FALSE, 1),
       (4, 'SMALL', 3.00, TRUE, 2),
       (5, 'MEDIUM', 4.00, FALSE, 2),
       (6, 'LARGE', 5.00, FALSE, 2),
       (7, 'SMALL', 4.00, TRUE, 3),
       (8, 'MEDIUM', 5.00, FALSE, 3),
       (9, 'LARGE', 6.00, FALSE, 3),
       (10, 'SMALL', 3.00, TRUE, 4),
       (11, 'MEDIUM', 4.00, FALSE, 4),
       (12, 'LARGE', 5.00, FALSE, 4),
       (13, 'UNICO', 3.50, TRUE, 5),
       (14, 'UNICO', 2.50, TRUE, 6),
       (15, 'SMALL', 3.50, TRUE, 7),
       (16, 'MEDIUM', 4.50, FALSE, 7),
       (17, 'LARGE', 5.50, FALSE, 7),
       (18, 'UNICO', 5.00, TRUE, 8);

SELECT setval(pg_get_serial_sequence('productos', 'id'), (SELECT MAX(id) FROM productos));
SELECT setval(pg_get_serial_sequence('producto_sizes', 'id'), (SELECT MAX(id) FROM producto_sizes));
