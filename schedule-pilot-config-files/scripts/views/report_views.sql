

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
ua.id,
ua.username,
CAST(a.count AS DECIMAL ) AS requests ,
b.count ::DECIMAL AS loans
FROM (
     	SELECT
		rci.user_account_id_fk,
		count(*)
		FROM schedule_pilot_db.request_check_in rci
		WHERE rci.created_date >= '2020-01-01' AND rci.created_date <= '2021-12-31'
		-- AND rci.user_account_id_fk = 14
		GROUP BY rci.user_account_id_fk
     ) AS a
INNER JOIN (
				SELECT
				rci.user_account_id_fk,
				count(*)
				FROM schedule_pilot_db.ticket_check_in tci
				INNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id
				WHERE tci.created_date >= '2020-01-01' AND tci.created_date <= '2021-12-31'
				-- AND rci.user_account_id_fk = 14
GROUP BY rci.user_account_id_fk
           ) AS b ON a.user_account_id_fk = b.user_account_id_fk
INNER JOIN schedule_pilot_db.user_account ua ON a.user_account_id_fk = ua.id;



-- a
SELECT
rci.user_account_id_fk,
rci.created_date
FROM schedule_pilot_db.request_check_in rci;

-- b
SELECT
rci.user_account_id_fk,
tci.created_date
FROM schedule_pilot_db.ticket_check_in tci
INNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id;




-----
SELECT
ua.username,
pco.created_date AS penalty_date,
pco.price_penalty,
i."name" AS item_name,
tci.created_date,
tci.return_date
FROM schedule_pilot_db.penalty_check_out pco
INNER JOIN schedule_pilot_db.ticket_check_out tco ON pco.ticket_check_out_id_fk = tco.id
INNER JOIN schedule_pilot_db.ticket_check_in tci ON tco.ticket_check_in_id_fk = tci.id
INNER JOIN schedule_pilot_db.item i ON tci.item_id_fk = i.id
INNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id
INNER JOIN schedule_pilot_db.user_account ua ON rci.user_account_id_fk = ua.id;


SELECT
ua.id,
ua.username,
sum(pco.price_penalty)
FROM schedule_pilot_db.penalty_check_out pco
INNER JOIN schedule_pilot_db.ticket_check_out tco ON pco.ticket_check_out_id_fk = tco.id
INNER JOIN schedule_pilot_db.ticket_check_in tci ON tco.ticket_check_in_id_fk = tci.id
INNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id
INNER JOIN schedule_pilot_db.user_account ua ON rci.user_account_id_fk = ua.id
GROUP BY ua.id, ua.username;


SELECT * FROM schedule_pilot_db.penalty_check_out;





