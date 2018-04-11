-- Database: CurrencyConverter

-- DROP DATABASE "CurrencyConverter";

CREATE DATABASE "CurrencyConverter"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- DROP TABLE IF EXISTS "Exchange_Rates";

CREATE TABLE public."Exchange_Rates"
	("Starting_Currency" character varying  (5),
    "Ending_Currency" character varying  (5),
    "Exchange_Rate" numeric (11,6),
    "Insert_Dt_Time" timestamp without time zone)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."Exchange_Rates"
    OWNER to postgres;
    
-- DROP TABLE IF EXISTS "Flags";

CREATE TABLE public."Flags"
	("Country" character varying (5),
    "Flag_Image" bytea)
WITH (
	OIDS = FALSE
);

ALTER TABLE public."Flags"
	OWNER to postgres;