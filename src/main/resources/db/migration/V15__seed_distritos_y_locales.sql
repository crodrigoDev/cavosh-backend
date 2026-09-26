-- Locales ficticios para desarrollo. Direcciones y coordenadas son ilustrativas.
INSERT INTO distritos (id, nombre)
VALUES (1, 'Miraflores'),
       (2, 'San Isidro'),
       (3, 'Barranco');

INSERT INTO locales (id, nombre, direccion, horario, latitud, longitud, activo, frecuente, distrito_id)
VALUES (1, 'Cavosh Cafe', 'Av. Ejemplo 100, Miraflores', '08:00 - 22:00', -12.1210000, -77.0300000, TRUE, TRUE, 1),
       (2, 'Cavosh Cafe', 'Av. Ejemplo 200, San Isidro', '08:00 - 22:00', -12.0970000, -77.0350000, TRUE, TRUE, 2),
       (3, 'Cavosh Cafe', 'Av. Ejemplo 300, Barranco', '09:00 - 21:00', -12.1490000, -77.0210000, TRUE, FALSE, 3);

SELECT setval(pg_get_serial_sequence('distritos', 'id'), (SELECT MAX(id) FROM distritos));
SELECT setval(pg_get_serial_sequence('locales', 'id'), (SELECT MAX(id) FROM locales));
