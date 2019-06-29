create table licenses(
	license_id character varying NOT NULL primary key,
	organization_id character varying NOT NULL,
	product_name character varying NOT NULL,
	license_type character varying NOT NULL,
	license_max int NOT NULL,
	license_allocated int NOT NULL,
	comment character varying
)