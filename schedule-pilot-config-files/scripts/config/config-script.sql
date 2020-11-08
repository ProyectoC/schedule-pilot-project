DROP SCHEMA IF EXISTS schedule_pilot_db CASCADE;
CREATE SCHEMA schedule_pilot_db;

-- Verifique this tables.
SELECT * FROM college_career cc;
SELECT * FROM item_status is2;
SELECT * FROM notification_type nt;
SELECT * FROM "parameter" p;
SELECT * FROM permission_account pa;
SELECT * FROM product_request_status prs;
SELECT * FROM product_status ps;
SELECT * FROM rol_account ra;
SELECT * FROM ticket_check_status tcs;
SELECT * FROM token_type tt;

-- Manage user
SELECT * FROM user_account ua;
SELECT * FROM product p;
SELECT * FROM product_rol pr;
SELECT * FROM item i;

DELETE FROM product_rol;
DELETE FROM product;

ALTER TABLE product_rol
DROP COLUMN id;

--DELETE FROM user_account;

SELECT now(); 

-- Manage Items
SELECT * FROM item_status is2;

SELECT * FROM item i;
SELECT * FROM item_detail id;

DROP TABLE item_detail;


DELETE FROM item_detail;
DELETE FROM item;

-- Manage
SELECT * FROM request_check_in rci;
SELECT * FROM request_check_in_product rcip;







SELECT nextval('track_id_request_check_in'); 














