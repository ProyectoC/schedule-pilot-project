ALTER TABLE user_account ADD COLUMN phone_number TEXT;

UPDATE user_account SET phone_number = floor(random()* (100000-1 + 1) + 1);

CREATE UNIQUE INDEX idx_user_account_phone_number
ON user_account(phone_number);

ALTER TABLE user_account ALTER COLUMN phone_number SET NOT NULL;

ALTER TABLE user_account ADD COLUMN phone_country_id_fk int8;

ALTER TABLE user_account ADD CONSTRAINT phone_country_id_fk FOREIGN KEY (phone_country_id_fk) REFERENCES country(id);

UPDATE user_account SET phone_country_id_fk = 47;

ALTER TABLE user_account ALTER COLUMN phone_country_id_fk SET NOT NULL;