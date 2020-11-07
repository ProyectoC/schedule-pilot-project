INSERT INTO product_request_status(id, created_by, created_date, is_active, description, "name")
VALUES(1, 'default_script', now(), TRUE, 'Indica que es necesario buscar un item para el producto requerido.', 'SOLICITADO');
INSERT INTO product_request_status(id, created_by, created_date, is_active, description, "name")
VALUES(2, 'default_script', now(), TRUE, 'Indica que se encontro un item para el producto requerido.', 'ENCONTRADO');
INSERT INTO product_request_status(id, created_by, created_date, is_active, description, "name")
VALUES(3, 'default_script', now(), TRUE, 'Indica que no se encontro un item para el producto requerido.', 'NO ENCONTRADO');