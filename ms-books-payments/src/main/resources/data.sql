-- Crear pedido de prueba
INSERT INTO orders (id, user_id, total, date)
VALUES (1, 'usuario123', 50.0, CURRENT_TIMESTAMP);

-- Ítems del pedido (asociados al pedido con id 1)
INSERT INTO order_item (id, book_id, nombre, cantidad, precio, order_id)
VALUES
    (1, 1, 'Cien años de soledad', 1, 25.0, 1),
    (2, 2, 'El Principito', 1, 25.0, 1);
