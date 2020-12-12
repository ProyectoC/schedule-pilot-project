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

delete from user_account ser;

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
SELECT * FROM request_check_in_product rcip ORDER BY created_date ASC;

SELECT * FROM product_request_status prs;


SELECT * FROM ticket_check_status tcs;
SELECT * FROM ticket_check_in tci;


ALTER TABLE request_check_in_product
ADD COLUMN attempts numeric DEFAULT 0;

ALTER TABLE request_check_in_product
DROP COLUMN count;

SELECT * FROM ticket_check_out tco;


SELECT nextval('schedule_pilot_db.track_id_request_check_in');
SELECT nextval('schedule_pilot_db.track_id_ticket_check_in');



SELECT * FROM product p;
SELECT * FROM item i;
SELECT * FROM item_status is2;
SELECT * FROM item_detail id;
SELECT 

SELECT * FROM request_check_in;
SELECT * FROM request_check_in_product rcip;

SELECT * FROM product_request_status prs;

SELECT * FROM product p;
SELECT * FROM product_rol pr;
SELECT * FROM product_status ps;
SELECT * FROM rol_account ra;


SELECT * FROM ticket_check_in tci;
SELECT * FROM ticket_check_status tcs;

select * from ticket_check_out tco;

SELECT * FROM ticket_check_log tcl;

SELECT * FROM item_status is2;


SELECT * FROM college_career cc;


SELECT * FROM notification n;
SELECT VERSION();
SELECT * FROM request_check_in rci;
SELECT * FROM request_check_in_product rcip;


SELECT * FROM user_account ua;

delete from user_account;


INSERT INTO usar

select now();

SELECT * FROM user_account ua;
SELECT * FROM rol_account ra;

SELECT ua.username FROM rol_account ra
INNER JOIN user_account ua ON ra.id = ua.rol_account_id_fk
WHERE ra."name" = 'Estudiante';

SELECT now();

SELECT now_current();

SELECT * FROM user_account uas;

DELETE FROM user_account WHERE id = 5;

SELECT * FROM country;

DELETE FROM country WHERE id NOT IN (47);

SELECT * FROM country c;

SELECT * FROM notification n;







