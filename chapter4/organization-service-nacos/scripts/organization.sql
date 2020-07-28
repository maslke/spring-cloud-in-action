create table organization
(
  organization_id character varying NOT NULL primary key,
  name            character varying NOT NULL,
  contact_name    character varying NOT NULL,
  contact_email   character varying NOT NULL,
  contact_phone   character varying NOT NULL
)