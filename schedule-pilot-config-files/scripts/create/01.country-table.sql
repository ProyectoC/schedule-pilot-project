CREATE TABLE IF NOT EXISTS country (
  id serial NOT NULL,
  iso char(2) NOT NULL,
  name varchar(80) NOT NULL,
  nicename varchar(80) NOT NULL,
  iso3 char(3) DEFAULT NULL,
  numcode numeric(6) DEFAULT NULL,
  phonecode numeric(5) NOT NULL,
  PRIMARY KEY (id)
);