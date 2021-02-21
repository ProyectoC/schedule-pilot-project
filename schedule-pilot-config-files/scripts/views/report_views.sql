

--DROP VIEW v_chart_loan_product;
CREATE OR REPLACE VIEW v_chart_loan_product
AS
SELECT
row_number() OVER () AS id,
rci.user_account_id_fk AS user_account_id,
ua.username,
p."name" AS product_name,
p.description AS product_description,
i.name AS item_name,
tci.created_date AS date
FROM ticket_check_in tci
INNER JOIN item i ON tci.item_id_fk = i.id
INNER JOIN product p ON i.product_id_fk = p.id
INNER JOIN request_check_in rci ON tci.request_check_in_id_fk = rci.id
INNER JOIN user_account ua ON rci.user_account_id_fk = ua.id;

SELECT * FROM v_chart_loan_product;

-- User
SELECT
c.product_name,
count(*)
FROM v_chart_loan_product c
WHERE c.date >= '2020-01-01' AND c.date <= '2021-12-31'
AND c.user_account_id = 14
GROUP BY c.user_account_id, c.product_name;

-- Admin
SELECT
c.product_name AS productName,
count(*) AS totalLoans
FROM v_chart_loan_product c
WHERE c.date >= '2020-01-01' AND c.date <= '2021-12-31'
GROUP BY c.product_name;


SELECT * FROM rol_account ra;


-----
SELECT
c.user_account_id,
c.username,
count(*)
FROM v_chart_loan_product c
WHERE c.date >= '2020-01-01' AND c.date <= '2021-12-31'
-- AND c.user_account_id = 14
GROUP BY c.user_account_id, c.username
ORDER BY count(*) DESC;



-----
CREATE VIEW v_chart_requests_vs_loans
AS
SELECT
row_number() OVER () AS id,
ua.username,
a.count AS requests,
b.count AS loans
FROM (SELECT
rci.user_account_id_fk,
count(*)
FROM request_check_in rci
GROUP BY rci.user_account_id_fk) AS a
INNER JOIN (SELECT
rci.user_account_id_fk,
count(*)
FROM schedule_pilot_db.ticket_check_in tci
INNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id
GROUP BY rci.user_account_id_fk
) AS b ON a.user_account_id_fk = b.user_account_id_fk
INNER JOIN schedule_pilot_db.user_account ua ON a.user_account_id_fk = ua.id;

-- a
SELECT
rci.user_account_id_fk,
count(*)
FROM request_check_in rci
GROUP BY rci.user_account_id_fk

-- b
SELECT
rci.user_account_id_fk,
count(*)
FROM schedule_pilot_db.ticket_check_in tci
INNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id
GROUP BY rci.user_account_id_fk;










