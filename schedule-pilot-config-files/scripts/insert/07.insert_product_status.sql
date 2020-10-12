INSERT INTO product_status(id, created_by, created_date, last_modified_by, last_modified_date, is_active, name)
VALUES(nextval('product_status_sequence_key_id'), 'default_script', NOW(), 'default_script', NOW(), TRUE, 'Agotado');
INSERT INTO product_status(id,created_by, created_date, last_modified_by, last_modified_date, is_active, name)
VALUES(nextval('product_status_sequence_key_id'), 'default_script', NOW(), 'default_script', NOW(), TRUE, 'Disponible');
INSERT INTO product_status(id, created_by, created_date, last_modified_by, last_modified_date, is_active, name)
VALUES(nextval('product_status_sequence_key_id'), 'default_script', NOW(), 'default_script', NOW(), TRUE, 'En prestamo');